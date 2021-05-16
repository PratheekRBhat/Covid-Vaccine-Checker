package com.pratheek.covidvaccinechecker.api

import com.pratheek.covidvaccinechecker.models.*
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CowinAPI {
    @POST("/api/v2/auth/public/generateOTP")
    fun generateOTP(
        @Body json: RequestBody
    ): Call<SendOTPResponse>

    @POST("/api/v2/auth/public/confirmOTP")
    fun confirmOTP(
        @Body json: RequestBody
    ): Call<ConfirmOTPResponse>

    @GET("/api/v2/appointment/sessions/public/findByPin")
    fun getCenterByPin(
        @Query("pincode")
        pincode: String,
        @Query("date")
        date: String
    ): Call<CentersResponse>

    @GET("/api/v2/appointment/sessions/public/findByDistrict")
    fun getCenterByDistrict(
        @Query("district_id")
        districtId: String,
        @Query("date")
        date: String
    ): Call<CentersResponse>

    @GET("/api/v2/admin/location/states")
    fun getStatesList(): Call<StatesResponse>

    @GET("/api/v2/admin/location/districts")
    fun getDistrictList(
        @Query("state_id")
        stateId: String
    ): Call<DistrictsResponse>
}