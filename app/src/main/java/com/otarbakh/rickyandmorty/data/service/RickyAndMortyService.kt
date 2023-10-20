package com.otarbakh.rickyandmorty.data.service

import com.otarbakh.rickyandmorty.data.model.characters.CharactersDto
import com.otarbakh.rickyandmorty.data.model.characters.singlecharacter.SingleCharacterDto
import com.otarbakh.rickyandmorty.data.model.episodes.EpisodesDto
import com.otarbakh.rickyandmorty.data.model.episodes.multipleepisode.MultipleEpisodesDto
import com.otarbakh.rickyandmorty.data.model.episodes.singleepisode.SingleEpisodeDto
import com.otarbakh.rickyandmorty.data.model.locations.LocationsDto
import com.otarbakh.rickyandmorty.data.model.locations.mutliplecharacters.MultipleCharactersDto
import com.otarbakh.rickyandmorty.data.model.locations.singlelocation.SingleLocationDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickyAndMortyService {

    // yvela characteri
    @GET("character")
    suspend fun fetchCharacters(
        @Query("page") page: Int? = null
    ): Response<CharactersDto>

    // yvela lokacia
    @GET("location")
    suspend fun fetchLocations(
        @Query("page") page: Int? = null
    ): Response<LocationsDto>

    // yvela epizodi
    @GET("episode")
    suspend fun fetchEpisodes(
        @Query("page") page: Int? = null
    ): Response<EpisodesDto>

    //searched characterebi
    @GET("character")
    suspend fun fetchSearchedCharacters(
        @Query("name") name: String? = null
    ): Response<CharactersDto>

    //daklikebis dros gadasvla ert characterze
    @GET("character/{id}")
    suspend fun fetchSingleCharacter(
        @Path("id") id: Int
    ): Response<SingleCharacterDto>

    // tito characteristvis epizodebis sia
    @GET("episode/{ids}")
    suspend fun fetchSingleCharacter(
        @Path("ids") ids: List<Int>
    ): Response<MultipleEpisodesDto>

    // daklikebis dros gadasvla ert locationze
    @GET("location/{id}")
    suspend fun fetchSingleLocation(
        @Path("id") id: Int
    ): Response<SingleLocationDto>

    // tito locationistvis characterebis sia
    @GET("character/{ids}")
    suspend fun fetchMultipleCharacters(
        @Path("ids") ids: List<Int>
    ): Response<MultipleCharactersDto>

    // episode

    @GET("episode/{id}")
    suspend fun fetchSingleEpisode(
        @Path("id") id: Int
    ): Response<SingleEpisodeDto>

}