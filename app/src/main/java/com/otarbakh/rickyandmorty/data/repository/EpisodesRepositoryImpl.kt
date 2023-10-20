package com.otarbakh.rickyandmorty.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.otarbakh.rickyandmorty.common.Resource
import com.otarbakh.rickyandmorty.data.database.dao.EpisodesDao
import com.otarbakh.rickyandmorty.data.database.model.CharactersEntity
import com.otarbakh.rickyandmorty.data.database.model.EpisodesEntity
import com.otarbakh.rickyandmorty.data.model.episodes.EpisodesResult
import com.otarbakh.rickyandmorty.data.model.episodes.singleepisode.SingleEpisodeDto
import com.otarbakh.rickyandmorty.data.model.locations.mutliplecharacters.MultipleCharactersDto
import com.otarbakh.rickyandmorty.data.remotemediator.CharactersRemoteMediator
import com.otarbakh.rickyandmorty.data.remotemediator.EpisodesRemoteMediator
import com.otarbakh.rickyandmorty.data.service.RickyAndMortyService
import com.otarbakh.rickyandmorty.domain.repository.EpisodesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

class EpisodesRepositoryImpl @Inject constructor(
    private val rickyAndMortyService: RickyAndMortyService,
    private val episodesDao: EpisodesDao
) : EpisodesRepository {
    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getEpisodes(): Flow<PagingData<EpisodesEntity>> {
        val pagingSourceFactory = { episodesDao.getEpisodes() }
        return Pager(
            config = PagingConfig(pageSize = 25),
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = EpisodesRemoteMediator(rickyAndMortyService, episodesDao)
        ).flow
    }

    override suspend fun getMultipleCharacters(ids: List<Int>): Flow<Resource<MultipleCharactersDto>> = flow {
            emit(Resource.Loading(true))
            val response = rickyAndMortyService.fetchMultipleCharacters(ids)
            if (response?.isSuccessful!!) {
                emit(Resource.Success(response.body()!!))
            } else {

                val errorBody = response?.errorBody()?.string()
                val errorMessage = if (errorBody != null) {
                    if (response?.headers()?.get("Content-Type")
                            ?.contains("application/json") == true
                    ) {
                        try {
                            val errorJson = JSONObject(errorBody)
                            errorJson.getString("message")
                        } catch (e: JSONException) {
                            "An error occurred json"
                        }
                    } else {
                        "An error occurred outer"
                    }
                } else {
                    "An error occurred outer"
                }
                emit(Resource.Error(errorMessage))
            }
        }

    override suspend fun getSingleEpisode(id: Int): Flow<Resource<SingleEpisodeDto>> = flow {
        emit(Resource.Loading(true))
        val response = rickyAndMortyService.fetchSingleEpisode(id)
        if (response?.isSuccessful!!) {
            emit(Resource.Success(response.body()!!))
        } else {

            val errorBody = response?.errorBody()?.string()
            val errorMessage = if (errorBody != null) {
                if (response?.headers()?.get("Content-Type")
                        ?.contains("application/json") == true
                ) {
                    try {
                        val errorJson = JSONObject(errorBody)
                        errorJson.getString("message")
                    } catch (e: JSONException) {
                        "An error occurred json"
                    }
                } else {
                    "An error occurred outer"
                }
            } else {
                "An error occurred outer"
            }
            emit(Resource.Error(errorMessage))
        }
    }

}

