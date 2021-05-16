package com.pratheek.covidvaccinechecker.util

import android.content.Context
import android.content.SharedPreferences
import com.pratheek.covidvaccinechecker.util.Constants.Companion.PREF_LOGIN_INFO
import com.pratheek.covidvaccinechecker.util.Constants.Companion.PREF_MODE_PRIVATE
import com.pratheek.covidvaccinechecker.util.Constants.Companion.PREF_NAME

class MyPreferences(context: Context) {
    private var sharedPrefs: SharedPreferences? = null

    init {
        sharedPrefs = context.getSharedPreferences(PREF_NAME, PREF_MODE_PRIVATE)
    }

    fun saveUserLogIn(value: Boolean) {
        sharedPrefs?.edit()?.putBoolean(PREF_LOGIN_INFO, value)?.apply()
    }

    fun isUserLoggedIn(): Boolean {
        return sharedPrefs?.getBoolean(PREF_LOGIN_INFO, false)!!
    }

    fun clearAllPreferences() {
        sharedPrefs?.edit()?.clear()?.apply()
    }
}