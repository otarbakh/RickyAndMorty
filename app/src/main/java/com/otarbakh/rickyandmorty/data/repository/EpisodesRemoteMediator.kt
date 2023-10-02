package com.otarbakh.rickyandmorty.data.repository

import android.net.Uri
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.RemoteMediator
import androidx.paging.LoadType
import androidx.paging.PagingState
import com.otarbakh.rickyandmorty.data.database.model.CharactersEntity
import com.otarbakh.rickyandmorty.data.database.RickAndMortyDao
import com.otarbakh.rickyandmorty.data.database.model.EpisodesEntity
import com.otarbakh.rickyandmorty.data.model.characters.toCharacter
import com.otarbakh.rickyandmorty.data.model.episodes.toEpisodes
import com.otarbakh.rickyandmorty.data.service.RickyAndMortyService

@OptIn(ExperimentalPagingApi::class)
class EpisodesRemoteMediator(
    private val apiService: RickyAndMortyService,
    private val rickyAndMortyDao: RickAndMortyDao,
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

            Log.d("kerdzobina",nextPageNumber.toString())

            val uri = Uri.parse(response.body()!!.info?.next)
            val nextPageQuery = uri.getQueryParameter("page")

            rickyAndMortyDao.insertAllEpisodes(response.body()!!.results!!.map { it!!.toEpisodes() })

            nextPageNumber = nextPageQuery?.toInt()!!

            MediatorResult.Success(endOfPaginationReached = response.body()!!.info?.next == null)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

}