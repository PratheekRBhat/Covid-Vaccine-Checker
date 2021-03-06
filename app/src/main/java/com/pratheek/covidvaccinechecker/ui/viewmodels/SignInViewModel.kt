package com.pratheek.covidvaccinechecker.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pratheek.covidvaccinechecker.repository.SignInRepo
import kotlinx.coroutines.launch

class SignInViewModel(application: Application) : AndroidViewModel(application) {
    var signInRepo: SignInRepo? = null

    init {
        signInRepo = SignInRepo(application)
    }

    fun generateOTP(body: String) = viewModelScope.launch { safeGenerateOTP(body) }

    fun confirmOTP(body: String) = viewModelScope.launch { safeConfirmOTP(body) }

    private fun safeGenerateOTP(body: String) {
        signInRepo?.generateOTP(body)
    }

    private fun safeConfirmOTP(body: String) {
        signInRepo?.confirmOTP(body)
    }
}