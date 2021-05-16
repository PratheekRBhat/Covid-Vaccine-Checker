package com.pratheek.covidvaccinechecker.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.pratheek.covidvaccinechecker.R
import com.pratheek.covidvaccinechecker.ui.viewmodels.SignInViewModel
import com.pratheek.covidvaccinechecker.util.MyPreferences
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import java.security.MessageDigest

class SignInActivity : AppCompatActivity() {
    private lateinit var signInViewModel: SignInViewModel
    private var myPreferences: MyPreferences? = null
    private var txnIdFromAPI: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        myPreferences = MyPreferences(this@SignInActivity)
        if (myPreferences?.isUserLoggedIn()!!) {
            Toast.makeText(this@SignInActivity, "Welcome back", Toast.LENGTH_SHORT).show()
//            TODO("Add a Loader and an Intent to the next activity")
        }

        initViews()
        initSignInViewModel()
    }

    private fun initViews() {
        etPhoneNo.doAfterTextChanged { text ->
            if (text?.length == 10)
                btnGetOtp.isEnabled = true
        }
        
        btnGetOtp.setOnClickListener {
            btnGetOtp.isEnabled = false
            val apiBody = JSONObject()
            apiBody.put("mobile", etPhoneNo.text.toString())

            signInViewModel.generateOTP(apiBody.toString())
        }

        etOTP.doAfterTextChanged { text ->
            if (text?.length == 6)
                btnSignIn.isEnabled = true
        }

        btnSignIn.setOnClickListener {
            btnSignIn.isEnabled = false
            val encodedOTP = hashString("SHA-256", etOTP.text.toString())

            val apiBody = JSONObject()
            apiBody.put("otp", encodedOTP)
            apiBody.put("txnId", txnIdFromAPI)

            signInViewModel.confirmOTP(apiBody.toString())
        }
    }

    private fun initSignInViewModel() {
        signInViewModel = SignInViewModel(this.application)

        signInViewModel.signInRepo?.sendOTPResponse?.observe(this, {
            if (it?.txnId != null)
                txnIdFromAPI = it.txnId
//            TODO("Close the loader")
//            TODO("Get txnId from SharedPrefs")
        })

        signInViewModel.signInRepo?.confirmOTPResponse?.observe(this, {
            if (it != null) {
                if (it.token != null) {
                    myPreferences?.saveUserLogIn(true)
                    Toast.makeText(this@SignInActivity, "Login success!", Toast.LENGTH_SHORT).show()
//                    TODO("Intent to new activity")
                } else {
                    Toast.makeText(this@SignInActivity, "Wrong OTP", Toast.LENGTH_SHORT).show()
                    btnSignIn.isEnabled = false
                }
            }
        })
    }

    private fun hashString(type: String, input: String): String {
        val hexChars = "0123456789abcdef"
        val bytes = MessageDigest
            .getInstance(type)
            .digest(input.toByteArray())
        val result = StringBuilder(bytes.size * 2)

        bytes.forEach {
            val i = it.toInt()
            result.append(hexChars[i shr 4 and 0x0f])
            result.append(hexChars[i and 0x0f])
        }

        return result.toString()
    }
}