package com.otarbakh.rickyandmorty.domain.repository

import androidx.paging.PagingData
import com.otarbakh.rickyandmorty.common.Resource
import com.otarbakh.rickyandmorty.data.database.model.CharactersEntity
import com.otarbakh.rickyandmorty.data.database.model.EpisodesEntity
import com.otarbakh.rickyandmorty.data.database.model.LocationsEntity
import com.otarbakh.rickyandmorty.data.model.characters.singlecharacter.SingleCharacterDto
import com.otarbakh.rickyandmorty.data.model.episodes.EpisodesDto
import com.otarbakh.rickyandmorty.data.model.episodes.multipleepisode.MultipleEpisodesDto
import com.otarbakh.rickyandmorty.data.model.locations.LocationsDto
import kotlinx.coroutines.flow.Flow

interface RickAndMortyRepository {

    suspend fun getCharacters(): Flow<PagingData<CharactersEntity>>

    suspend fun getLocations(): Flow<PagingData<LocationsEntity>>

    suspend fun getEpisodes(): Flow<PagingData<EpisodesEntity>>

    suspend fun getSingleCharacter(id: Int): Flow<Resource<SingleCharacterDto>>

    suspend fun getMultipleEpisodes(ids: List<Int>): Flow<Resource<MultipleEpisodesDto>>
}