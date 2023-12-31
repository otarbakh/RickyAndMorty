package com.otarbakh.rickyandmorty.data.model.episodes.multipleepisode

data class MultipleEpisodesDtoItem(
    val air_date: String?,
    val characters: List<String?>?,
    val created: String?,
    val episode: String?,
    val id: Int?,
    val name: String?,
    val url: String?
)