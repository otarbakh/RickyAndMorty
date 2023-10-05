package com.otarbakh.rickyandmorty.data.service

import com.otarbakh.rickyandmorty.data.model.characters.CharactersDto
import com.otarbakh.rickyandmorty.data.model.characters.singlecharacter.SingleCharacterDto
import com.otarbakh.rickyandmorty.data.model.episodes.EpisodesDto
import com.otarbakh.rickyandmorty.data.model.locations.LocationsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickyAndMortyService {

    @GET("character")
    suspend fun fetchCharacters(
        @Query("page") page: Int? = null
    ): Response<CharactersDto>

    @GET("location")
    suspend fun fetchLocations(
        @Query("page") page: Int? = null
    ): Response<LocationsDto>

    @GET("episode")
    suspend fun fetchEpisodes(
        @Query("page") page: Int? = null
    ): Response<EpisodesDto>

    @GET("character/{id}")
    suspend fun fetchSingleCharacter(
        @Path("id") id: Int

    ): Response<SingleCharacterDto>

}