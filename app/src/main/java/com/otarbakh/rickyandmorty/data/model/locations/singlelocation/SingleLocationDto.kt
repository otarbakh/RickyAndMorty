package com.otarbakh.rickyandmorty.data.model.locations.singlelocation

data class SingleLocationDto(
    val created: String,
    val dimension: String,
    val id: Int,
    val name: String,
    val residents: List<String>,
    val type: String,
    val url: String
)