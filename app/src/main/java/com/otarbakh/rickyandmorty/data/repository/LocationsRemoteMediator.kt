package com.otarbakh.rickyandmorty.data.repository

import android.net.Uri
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.RemoteMediator
import androidx.paging.LoadType
import androidx.paging.PagingState
import com.otarbakh.rickyandmorty.data.database.model.CharactersEntity
import com.otarbakh.rickyandmorty.data.database.RickAndMortyDao
import com.otarbakh.rickyandmorty.data.database.model.LocationsEntity
import com.otarbakh.rickyandmorty.data.model.characters.toCharacter
import com.otarbakh.rickyandmorty.data.model.locations.toLocation
import com.otarbakh.rickyandmorty.data.service.RickyAndMortyService

@OptIn(ExperimentalPagingApi::class)
class LocationsRemoteMediator(
    private val apiService: RickyAndMortyService,
    private val rickyAndMortyDao: RickAndMortyDao,
) : RemoteMediator<Int, LocationsEntity>() {


    private var nextPageNumber:Int? = 1


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LocationsEntity>,
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    null
                }
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> nextPageNumber
            }

            val response = apiService.fetchLocations(loadKey)

            Log.d("AmerikisPrezidenti",response.body()!!.results.toString())

            val uri = Uri.parse(response.body()!!.info?.next)
            val nextPageQuery = uri.getQueryParameter("page")

            rickyAndMortyDao.insertAllLocations(response.body()!!.results.map { it.toLocation() })

            nextPageNumber = nextPageQuery?.toInt()!!

            MediatorResult.Success(endOfPaginationReached = response.body()!!.info?.next == null)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}