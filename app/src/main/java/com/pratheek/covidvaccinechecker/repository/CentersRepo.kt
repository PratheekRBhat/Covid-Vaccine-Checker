package com.pratheek.covidvaccinechecker.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.pratheek.covidvaccinechecker.api.RetrofitInstance
import com.pratheek.covidvaccinechecker.models.CentersResponse
import com.pratheek.covidvaccinechecker.models.DistrictsResponse
import com.pratheek.covidvaccinechecker.models.StatesResponse
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CentersRepo(applicationContext: Context) {
    var centersResponse: MutableLiveData<CentersResponse>? = null
    var statesResponse: MutableLiveData<StatesResponse>? = null
    var districtsResponse: MutableLiveData<DistrictsResponse>? = null

    var application = applicationContext

    init {
        centersResponse = MutableLiveData()
        statesResponse = MutableLiveData()
        districtsResponse = MutableLiveData()
    }

    fun getCenterByPin(pincode: String, date:String): MutableLiveData<CentersResponse>? {
        RetrofitInstance.api.getCenterByPin(pincode, date).enqueue(object : Callback<CentersResponse>{
            override fun onResponse(
                call: Call<CentersResponse>,
                response: Response<CentersResponse>
            ) {
                if (checkApiResponse(response.code(), response.errorBody()))
                    centersResponse?.postValue(response.body())
            }

            override fun onFailure(call: Call<CentersResponse>, t: Throwable) {
                centersResponse?.postValue(null)
                Toast.makeText(application.applicationContext, "Please check your internet and try again.", Toast.LENGTH_SHORT).show()
            }
        })

        return centersResponse
    }

    fun getCentersByDistrict(districtId: String, date: String): MutableLiveData<CentersResponse>? {
        RetrofitInstance.api.getCenterByDistrict(districtId, date).enqueue(object : Callback<CentersResponse>{
            override fun onResponse(
                call: Call<CentersResponse>,
                response: Response<CentersResponse>
            ) {
                if (checkApiResponse(response.code(), response.errorBody()))
                    centersResponse?.postValue(response.body())

            }

            override fun onFailure(call: Call<CentersResponse>, t: Throwable) {
                centersResponse?.postValue(null)
                Toast.makeText(application.applicationContext, "Please check your internet and try again.", Toast.LENGTH_SHORT).show()
            }
        })

        return centersResponse
    }

    fun getStatesList(): MutableLiveData<StatesResponse>? {
        RetrofitInstance.api.getStatesList().enqueue(object : Callback<StatesResponse>{
            override fun onResponse(
                call: Call<StatesResponse>,
                response: Response<StatesResponse>
            ) {
                if (checkApiResponse(response.code(), response.errorBody()))
                    statesResponse?.postValue(response.body())
            }

            override fun onFailure(call: Call<StatesResponse>, t: Throwable) {
                statesResponse?.postValue(null)
                Toast.makeText(application.applicationContext, "Please check your internet and try again.", Toast.LENGTH_SHORT).show()
            }
        })

        return statesResponse
    }

    fun getDistrictsList(statesId: String): MutableLiveData<DistrictsResponse>? {
        RetrofitInstance.api.getDistrictList(statesId).enqueue(object : Callback<DistrictsResponse>{
            override fun onResponse(
                call: Call<DistrictsResponse>,
                response: Response<DistrictsResponse>
            ) {
                if (checkApiResponse(response.code(), response.errorBody()))
                    districtsResponse?.postValue(response.body())
            }

            override fun onFailure(call: Call<DistrictsResponse>, t: Throwable) {
                districtsResponse?.postValue(null)
                Toast.makeText(application.applicationContext, "Please check your internet and try again.", Toast.LENGTH_SHORT).show()
            }
        })

        return districtsResponse
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
                    val jObjError = JSONObject(errorBody!!.string())
                    Toast.makeText(application.applicationContext, "Error: ${jObjError.getString("error")}", Toast.LENGTH_LONG).show()
                    false
                } catch (e: Exception) {
                    Toast.makeText(application.applicationContext, "OTP Already Sent", Toast.LENGTH_LONG).show()
                    false
                }
            }
            else -> {
                return true
            }
        }
    }
}