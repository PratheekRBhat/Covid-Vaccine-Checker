package com.pratheek.covidvaccinechecker.models

data class StatesResponse(
    val states: List<State>?,
    val ttl: Int?
)