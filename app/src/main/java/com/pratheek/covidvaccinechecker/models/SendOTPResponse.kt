package com.pratheek.covidvaccinechecker.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SendOTPResponse(
    @SerializedName("txnId")
    @Expose
    val txnId: String?
)
