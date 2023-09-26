package com.otarbakh.rickyandmorty.data.model.characters

import com.otarbakh.rickyandmorty.data.database.model.CharactersEntity

data class Result(
    val created: String?,
    val episode: List<String?>?,
    val gender: String?,
    val id: Int?,
    val image: String?,
    val location: Location?,
    val name: String?,
    val origin: Origin?,
    val species: String?,
    val status: String?,
    val type: String?,
    val url: String?
)
fun Result.toCharacter(): CharactersEntity {
    return CharactersEntity(
        gender, id, image, name
    )
}