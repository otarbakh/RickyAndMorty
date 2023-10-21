package com.otarbakh.rickyandmorty.data.remotemediator

import android.net.Uri
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.otarbakh.rickyandmorty.data.database.dao.EpisodesDao
import com.otarbakh.rickyandmorty.data.database.model.EpisodesEntity
import com.otarbakh.rickyandmorty.data.model.episodes.toEpisode
import com.otarbakh.rickyandmorty.data.service.RickyAndMortyService

@OptIn(ExperimentalPagingApi::class)
class EpisodesRemoteMediator(
    private val apiService: RickyAndMortyService,
    private val episodesDao: EpisodesDao,
) : RemoteMediator<Int, EpisodesEntity>() {
    private var nextPageNumber:Int? = 1
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, EpisodesEntity>,
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    null
                }
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> nextPageNumber
            }


            val response = apiService.fetchEpisodes(loadKey)

            val uri = Uri.parse(response.body()!!.info?.next)
            val nextPageQuery = uri.getQueryParameter("page")

            episodesDao.insertAllEpisodes(response.body()!!.episodesResults.map { it.toEpisode() })
            nextPageNumber = nextPageQuery?.toInt()!!

            MediatorResult.Success(endOfPaginationReached = response.body()!!.info?.next == null)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}