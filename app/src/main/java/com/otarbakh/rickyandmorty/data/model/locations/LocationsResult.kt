package com.otarbakh.rickyandmorty.data.model.locations

data class LocationsResult(
    val created: String?,
    val dimension: String?,
    val id: Int?,
    val name: String?,
    val residents: List<String?>?,
    val type: String?,
    val url: String?
)