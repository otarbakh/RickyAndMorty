package com.otarbakh.rickyandmorty.data.model.locations

import com.otarbakh.rickyandmorty.data.database.model.CharactersEntity
import com.otarbakh.rickyandmorty.data.database.model.LocationsEntity
import com.otarbakh.rickyandmorty.data.model.characters.CharactersResult

data class LocationsResult(
    val created: String?,
    val dimension: String?,
    val id: Int?,
    val name: String?,
    val residents: List<String?>?,
    val type: String?,
    val url: String?
)

fun LocationsResult.toLocation(): LocationsEntity {
    return LocationsEntity(
        type, id, name
    )
}