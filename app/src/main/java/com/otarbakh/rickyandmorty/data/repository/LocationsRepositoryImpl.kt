package com.otarbakh.rickyandmorty.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.otarbakh.rickyandmorty.common.Resource
import com.otarbakh.rickyandmorty.data.database.dao.LocationsDao
import com.otarbakh.rickyandmorty.data.database.model.LocationsEntity
import com.otarbakh.rickyandmorty.data.model.locations.mutliplecharacters.MultipleCharactersDto
import com.otarbakh.rickyandmorty.data.model.locations.singlelocation.SingleLocationDto
import com.otarbakh.rickyandmorty.data.remotemediator.LocationsRemoteMediator
import com.otarbakh.rickyandmorty.data.service.RickyAndMortyService
import com.otarbakh.rickyandmorty.domain.repository.LocationsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

class LocationsRepositoryImpl @Inject constructor(
    private val rickyAndMortyService: RickyAndMortyService,
    private val locationsDao: LocationsDao
) : LocationsRepository {
    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getLocations(): Flow<PagingData<LocationsEntity>> {
        val pagingSourceFactory = { locationsDao.getLocations() }
        return Pager(
            config = PagingConfig(pageSize = 25),
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = LocationsRemoteMediator(rickyAndMortyService, locationsDao)
        ).flow
    }

    override suspend fun getSingleLocation(id: Int): Flow<Resource<SingleLocationDto>> = flow {
        emit(Resource.Loading(true))
        val response = rickyAndMortyService.fetchSingleLocation(id)
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

    override suspend fun getMultipleCharacters(ids: List<Int>): Flow<Resource<MultipleCharactersDto>> =
        flow {
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

}

