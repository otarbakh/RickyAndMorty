package com.otarbakh.rickyandmorty.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.otarbakh.rickyandmorty.common.Resource
import com.otarbakh.rickyandmorty.data.database.model.CharactersEntity
import com.otarbakh.rickyandmorty.data.database.RickAndMortyDao
import com.otarbakh.rickyandmorty.data.database.model.EpisodesEntity
import com.otarbakh.rickyandmorty.data.database.model.LocationsEntity
import com.otarbakh.rickyandmorty.data.model.episodes.EpisodesDto
import com.otarbakh.rickyandmorty.data.model.locations.LocationsDto
import com.otarbakh.rickyandmorty.data.service.RickyAndMortyService
import com.otarbakh.rickyandmorty.domain.repository.RickAndMortyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class RickAndMortyRepositoryImpl @Inject constructor(
    private val rickyAndMortyService: RickyAndMortyService,
    private val rickAndMortyDao: RickAndMortyDao
) : RickAndMortyRepository {
    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getCharacters(): Flow<PagingData<CharactersEntity>> {
        val pagingSourceFactory = { rickAndMortyDao.getCharacters() }
        return Pager(
            config = PagingConfig(pageSize = 25),
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = CharactersRemoteMediator(rickyAndMortyService, rickAndMortyDao)
        ).flow
    }

//    override suspend fun getLocations(): Flow<Resource<LocationsEntity>> {
//        val pagingSourceFactory = { rickAndMortyDao.getEpisodes() }
//        return Pager(
//            config = PagingConfig(pageSize = 25),
//            pagingSourceFactory = pagingSourceFactory,
//            remoteMediator = EpisodesRemoteMediator(rickyAndMortyService, rickAndMortyDao)
//        ).flow
//    }

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getEpisodes(): Flow<PagingData<EpisodesEntity>> {
        val pagingSourceFactory = { rickAndMortyDao.getEpisodes() }
        return Pager(
            config = PagingConfig(pageSize = 25),
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = EpisodesRemoteMediator(rickyAndMortyService, rickAndMortyDao)
        ).flow
    }

}

