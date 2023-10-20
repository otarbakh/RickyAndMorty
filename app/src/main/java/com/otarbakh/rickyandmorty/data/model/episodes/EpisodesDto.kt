package com.otarbakh.rickyandmorty.data.model.episodes


import com.google.gson.annotations.SerializedName
import com.otarbakh.rickyandmorty.data.database.model.EpisodesEntity
import com.otarbakh.rickyandmorty.data.database.model.LocationsEntity
import com.otarbakh.rickyandmorty.data.model.locations.LocationsResult

data class EpisodesDto(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val episodesResults: List<EpisodesResult>
)