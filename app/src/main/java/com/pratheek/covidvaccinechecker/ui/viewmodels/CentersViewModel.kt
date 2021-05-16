package com.pratheek.covidvaccinechecker.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pratheek.covidvaccinechecker.repository.CentersRepo
import kotlinx.coroutines.launch

class CentersViewModel(application: Application) : AndroidViewModel(application) {
    var centersRepo: CentersRepo? = null

    init {
        centersRepo = CentersRepo(application)
    }

    fun getCenterByPin(pincode: String, date: String) = viewModelScope.launch { safeGetCenterByPin(pincode, date) }

    fun getCenterByDistrict(districtId: String, date: String) = viewModelScope.launch { safeGetCenterByDistrict(districtId, date) }

    fun getStatesList() = viewModelScope.launch { safeGetStatesList() }

    fun getDistrictList(stateId: String) = viewModelScope.launch { safeGetDistrictList(stateId) }

    private fun safeGetCenterByPin(pincode: String, date: String) {
        centersRepo?.getCenterByPin(pincode, date)
    }

    private fun safeGetCenterByDistrict(districtId: String, date: String) {
        centersRepo?.getCentersByDistrict(districtId, date)
    }

    private fun safeGetStatesList() {
        centersRepo?.getStatesList()
    }

    private fun safeGetDistrictList(stateId: String) {
        centersRepo?.getDistrictsList(stateId)
    }
}