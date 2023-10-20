package com.otarbakh.rickyandmorty.data.model.episodes


import com.google.gson.annotations.SerializedName
import com.otarbakh.rickyandmorty.data.database.model.EpisodesEntity

data class EpisodesResult(
    @SerializedName("air_date")
    val airDate: String,
    @SerializedName("characters")
    val characters: List<String>,
    @SerializedName("created")
    val created: String,
    @SerializedName("episode")
    val episode: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)

fun EpisodesResult.toEpisode(): EpisodesEntity {
    return EpisodesEntity(
        airDate,id, name, episode, url
    )
}