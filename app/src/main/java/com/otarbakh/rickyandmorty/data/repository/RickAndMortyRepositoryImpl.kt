package com.otarbakh.rickyandmorty.data.repository

import android.util.Log
import com.otarbakh.rickyandmorty.common.Constants
import com.otarbakh.rickyandmorty.common.Resource
import com.otarbakh.rickyandmorty.data.model.characters.CharactersDto
import com.otarbakh.rickyandmorty.data.model.episodes.EpisodesDto
import com.otarbakh.rickyandmorty.data.model.locations.LocationsDto
import com.otarbakh.rickyandmorty.data.service.RickyAndMortyService
import com.otarbakh.rickyandmorty.domain.repository.RickAndMortyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class RickAndMortyRepositoryImpl @Inject constructor(
    private val rickyAndMortyService: RickyAndMortyService
):RickAndMortyRepository {
    override suspend fun getCharacters(): Flow<Resource<CharactersDto>> = flow {
        try {
            emit(Resource.Loading(true))
            val response = rickyAndMortyService.fetchCharacters()
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()!!))

                Log.d("Jameson", "${response}")
            } else {
                Log.d("Jameson", "erorrrrrrrr")
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Ops Erorr"))
            Log.d("Jameson", "erorr")
        } catch (e: HttpException) {
            emit(Resource.Error(e.message()))

            Log.d("Jameson", "eoriraa vaax")
        }
    }

    override suspend fun getLocations(): Flow<Resource<LocationsDto>> = flow {
        try {
            emit(Resource.Loading(true))
            val response = rickyAndMortyService.fetchLocations()
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()!!))

                Log.d("Jameson", "${response}")
            } else {
                Log.d("Jameson", "erorrrrrrrr")
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Ops Erorr"))
            Log.d("Jameson", "erorr")
        } catch (e: HttpException) {
            emit(Resource.Error(e.message()))

            Log.d("Jameson", "eoriraa vaax")
        }
    }

    override suspend fun getEpisodes(): Flow<Resource<EpisodesDto>> = flow {
        try {
            emit(Resource.Loading(true))
            val response = rickyAndMortyService.fetchEpisodes()
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()!!))

                Log.d("Jameson", "${response}")
            } else {
                Log.d("Jameson", "erorrrrrrrr")
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Ops Erorr"))
            Log.d("Jameson", "erorr")
        } catch (e: HttpException) {
            emit(Resource.Error(e.message()))

            Log.d("Jameson", "eoriraa vaax")
        }
    }
}