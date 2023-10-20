package com.otarbakh.rickyandmorty.data.remotemediator

import android.net.Uri
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.otarbakh.rickyandmorty.data.database.dao.LocationsDao
import com.otarbakh.rickyandmorty.data.database.model.LocationsEntity
import com.otarbakh.rickyandmorty.data.model.locations.toLocation
import com.otarbakh.rickyandmorty.data.service.RickyAndMortyService

@OptIn(ExperimentalPagingApi::class)
class LocationsRemoteMediator(
    private val apiService: RickyAndMortyService,
    private val locationsDao: LocationsDao,
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

            val uri = Uri.parse(response.body()!!.info?.next)
            val nextPageQuery = uri.getQueryParameter("page")

            locationsDao.insertAllLocations(response.body()!!.results.map { it.toLocation() })

            nextPageNumber = nextPageQuery?.toInt()!!

            MediatorResult.Success(endOfPaginationReached = response.body()!!.info?.next == null)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}