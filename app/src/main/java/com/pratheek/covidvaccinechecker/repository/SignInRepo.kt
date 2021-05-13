package com.pratheek.covidvaccinechecker.repository

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.pratheek.covidvaccinechecker.api.RetrofitInstance
import com.pratheek.covidvaccinechecker.models.ConfirmOTPResponse
import com.pratheek.covidvaccinechecker.models.SendOTPResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInRepo (applicationContext: Application){
    var sendOTPResponse: MutableLiveData<SendOTPResponse>? = null
    var confirmOTPResponse: MutableLiveData<ConfirmOTPResponse>? = null

    var application = applicationContext

    init {
        sendOTPResponse = MutableLiveData()
        confirmOTPResponse = MutableLiveData()
    }

    suspend fun generateOTP(body: String): MutableLiveData<SendOTPResponse>? {
        val request = body.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        RetrofitInstance.api.generateOTP(request).enqueue(object : Callback<SendOTPResponse> {
            override fun onResponse(
                call: Call<SendOTPResponse>,
                response: Response<SendOTPResponse>
            ) {
                if (checkApiResponse(response.code(), response.errorBody())) {
                    sendOTPResponse?.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<SendOTPResponse>, t: Throwable) {
                Toast.makeText(application.applicationContext, "Please check your internet and try again.", Toast.LENGTH_SHORT).show()
            }
        })
        return sendOTPResponse
    }

    suspend fun confirmOTP(body: String): MutableLiveData<ConfirmOTPResponse>? {
        val request = body.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        RetrofitInstance.api.confirmOTP(request).enqueue(object : Callback<ConfirmOTPResponse> {
            override fun onResponse(
                call: Call<ConfirmOTPResponse>,
                response: Response<ConfirmOTPResponse>
            ) {
               if (checkApiResponse(response.code(), response.errorBody())) {
                    confirmOTPResponse?.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ConfirmOTPResponse>, t: Throwable) {
                Toast.makeText(application.applicationContext, "Please check your internet and try again.", Toast.LENGTH_SHORT).show()
            }
        })

        return confirmOTPResponse
    }

    fun checkApiResponse(code : Int, errorBody: ResponseBody?): Boolean {
        when (code) {
            500 -> {
                Toast.makeText(application.applicationContext, "Internal Server Error", Toast.LENGTH_SHORT).show()
                return false
            }
            401 -> {
                Toast.makeText(application.applicationContext, "Unauthenticated access!", Toast.LENGTH_SHORT).show()
                return false
            }
            400 -> {
                return try {
                    val jObjError = JSONObject(errorBody.toString())
                    Toast.makeText(application.applicationContext, "Error: ${jObjError.getString("error")}", Toast.LENGTH_SHORT).show()
                    false
                } catch (e: Exception) {
                    Toast.makeText(application.applicationContext, e.message, Toast.LENGTH_LONG).show()
                    false
                }
            }
            else -> {
                return true
            }
        }
    }
}