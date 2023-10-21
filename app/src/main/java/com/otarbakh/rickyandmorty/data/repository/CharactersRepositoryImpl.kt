package com.otarbakh.rickyandmorty.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.otarbakh.rickyandmorty.common.Resource
import com.otarbakh.rickyandmorty.data.database.dao.CharactersDao
import com.otarbakh.rickyandmorty.data.database.model.CharactersEntity
import com.otarbakh.rickyandmorty.data.model.characters.CharactersResult
import com.otarbakh.rickyandmorty.data.model.characters.singlecharacter.SingleCharacterDto
import com.otarbakh.rickyandmorty.data.model.episodes.multipleepisode.MultipleEpisodesDto
import com.otarbakh.rickyandmorty.data.remotemediator.CharactersRemoteMediator
import com.otarbakh.rickyandmorty.data.service.RickyAndMortyService
import com.otarbakh.rickyandmorty.domain.repository.CharactersRepository
import com.otarbakh.rickyandmorty.domain.repository.EpisodesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val rickyAndMortyService: RickyAndMortyService,
    private val charactersDao: CharactersDao,
) : CharactersRepository {
    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getCharacters(): Flow<PagingData<CharactersEntity>> {
        val pagingSourceFactory = { charactersDao.getCharacters() }
        return Pager(
            config = PagingConfig(pageSize = 25),
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = CharactersRemoteMediator(rickyAndMortyService, charactersDao)
        ).flow
    }

    override suspend fun getSingleCharacter(id: Int): Flow<Resource<SingleCharacterDto>> = flow {
        emit(Resource.Loading(true))
        val response = rickyAndMortyService.fetchSingleCharacter(id)
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
    override suspend fun getSearchedCharacters(name: String): Flow<Resource<List<CharactersResult>>> =
        flow {
            emit(Resource.Loading(true))
            val response = rickyAndMortyService.fetchSearchedCharacters(name)
            if (response?.isSuccessful!!) {
                emit(Resource.Success(response.body()!!.results))
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

    override suspend fun getMultipleEpisodes(ids: List<Int>): Flow<Resource<MultipleEpisodesDto>> =
        flow {
            emit(Resource.Loading(true))
            val response = rickyAndMortyService.fetchSingleCharacter(ids)
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

