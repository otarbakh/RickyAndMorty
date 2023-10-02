package com.otarbakh.rickyandmorty.data.model.episodes


import com.otarbakh.rickyandmorty.data.database.model.EpisodesEntity

data class Result(
    val air_date: String?,
    val characters: List<String?>?,
    val created: String?,
    val episode: String?,
    val id: Int?,
    val name: String?,
    val url: String?
)


fun Result.toEpisodes(): EpisodesEntity {
    return EpisodesEntity(
        air_date, id, name,episode,url
    )
}