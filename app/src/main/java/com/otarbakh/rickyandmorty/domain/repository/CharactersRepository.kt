package com.otarbakh.rickyandmorty.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.otarbakh.rickyandmorty.common.Resource
import com.otarbakh.rickyandmorty.data.database.model.CharactersEntity
import com.otarbakh.rickyandmorty.data.database.model.LocationsEntity
import com.otarbakh.rickyandmorty.data.model.characters.CharactersResult
import com.otarbakh.rickyandmorty.data.model.characters.singlecharacter.SingleCharacterDto
import com.otarbakh.rickyandmorty.data.model.episodes.EpisodesResult
import com.otarbakh.rickyandmorty.data.model.episodes.multipleepisode.MultipleEpisodesDto
import com.otarbakh.rickyandmorty.data.model.episodes.singleepisode.SingleEpisodeDto
import com.otarbakh.rickyandmorty.data.model.locations.LocationsResult
import com.otarbakh.rickyandmorty.data.model.locations.mutliplecharacters.MultipleCharactersDto
import com.otarbakh.rickyandmorty.data.model.locations.singlelocation.SingleLocationDto
import com.otarbakh.rickyandmorty.domain.model.LocationsDomain
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {

    suspend fun getCharacters(): Flow<PagingData<CharactersEntity>>

    suspend fun getSingleCharacter(id: Int): Flow<Resource<SingleCharacterDto>>

    suspend fun getMultipleEpisodes(ids: List<Int>): Flow<Resource<MultipleEpisodesDto>>

    suspend fun getSearchedCharacters(name:String): Flow<Resource<List<CharactersResult>>>

}