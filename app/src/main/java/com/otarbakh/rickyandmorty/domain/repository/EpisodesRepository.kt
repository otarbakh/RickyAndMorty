package com.otarbakh.rickyandmorty.domain.repository

import androidx.paging.PagingData
import com.otarbakh.rickyandmorty.common.Resource
import com.otarbakh.rickyandmorty.data.database.model.CharactersEntity
import com.otarbakh.rickyandmorty.data.database.model.EpisodesEntity
import com.otarbakh.rickyandmorty.data.database.model.LocationsEntity
import com.otarbakh.rickyandmorty.data.model.characters.CharactersResult
import com.otarbakh.rickyandmorty.data.model.characters.singlecharacter.SingleCharacterDto
import com.otarbakh.rickyandmorty.data.model.episodes.EpisodesResult
import com.otarbakh.rickyandmorty.data.model.episodes.multipleepisode.MultipleEpisodesDto
import com.otarbakh.rickyandmorty.data.model.episodes.singleepisode.SingleEpisodeDto
import com.otarbakh.rickyandmorty.data.model.locations.mutliplecharacters.MultipleCharactersDto
import com.otarbakh.rickyandmorty.data.model.locations.singlelocation.SingleLocationDto
import kotlinx.coroutines.flow.Flow

interface EpisodesRepository {
    suspend fun getEpisodes(): Flow<PagingData<EpisodesEntity>>

    suspend fun getMultipleCharacters(ids: List<Int>): Flow<Resource<MultipleCharactersDto>>

    suspend fun getSingleEpisode(id: Int): Flow<Resource<SingleEpisodeDto>>

}