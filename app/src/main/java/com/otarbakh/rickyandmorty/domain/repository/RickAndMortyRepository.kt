package com.otarbakh.rickyandmorty.domain.repository

import androidx.paging.PagingData
import com.otarbakh.rickyandmorty.common.Resource
import com.otarbakh.rickyandmorty.data.model.characters.CharactersDto
import com.otarbakh.rickyandmorty.data.model.characters.Result
import com.otarbakh.rickyandmorty.data.model.episodes.EpisodesDto
import com.otarbakh.rickyandmorty.data.model.locations.LocationsDto
import kotlinx.coroutines.flow.Flow

interface RickAndMortyRepository {

    suspend fun getCharacters(): Flow<PagingData<Result>>

    suspend fun getLocations(): Flow<Resource<LocationsDto>>

    suspend fun getEpisodes(): Flow<Resource<EpisodesDto>>
}