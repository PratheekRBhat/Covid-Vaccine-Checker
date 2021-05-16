package com.pratheek.covidvaccinechecker.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ConfirmOTPResponse(
    @SerializedName("token")
    @Expose
    val token: String?
)