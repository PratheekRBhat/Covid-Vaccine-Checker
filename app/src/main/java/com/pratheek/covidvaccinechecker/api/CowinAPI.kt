package com.pratheek.covidvaccinechecker.api

import com.pratheek.covidvaccinechecker.models.ConfirmOTPResponse
import com.pratheek.covidvaccinechecker.models.SendOTPResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CowinAPI {
    @POST("/v2/auth/public/generateOTP")
    suspend fun generateOTP(
        @Body json: RequestBody
    ): Call<SendOTPResponse>

    @POST("/v2/auth/public/confirmOTP")
    suspend fun confirmOTP(
        @Body json: RequestBody
    ): Call<ConfirmOTPResponse>
}