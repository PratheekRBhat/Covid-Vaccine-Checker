package com.pratheek.covidvaccinechecker.models

data class DistrictsResponse(
    val districts: List<District>?,
    val ttl: Int?
)