package com.otarbakh.rickyandmorty.data.model.episodes.singleepisode

data class SingleEpisodeDto(
    val air_date: String,
    val characters: List<String>,
    val created: String,
    val episode: String,
    val id: Int,
    val name: String,
    val url: String
)