package com.otarbakh.rickyandmorty.data.repository

import android.net.Uri
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.RemoteMediator
import androidx.paging.LoadType
import androidx.paging.PagingState
import com.otarbakh.rickyandmorty.data.database.model.CharactersEntity
import com.otarbakh.rickyandmorty.data.database.RickAndMortyDao
import com.otarbakh.rickyandmorty.data.model.characters.toCharacter
import com.otarbakh.rickyandmorty.data.service.RickyAndMortyService

@OptIn(ExperimentalPagingApi::class)
class CharactersRemoteMediator(
    private val apiService: RickyAndMortyService,
    private val characterDao: RickAndMortyDao,
) : RemoteMediator<Int, CharactersEntity>() {


    private var nextPageNumber:Int? = 1


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharactersEntity>,
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    null
                }
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> nextPageNumber
            }

            val response = apiService.fetchCharacters(loadKey)

            Log.d("kerdzobina",nextPageNumber.toString())

            val uri = Uri.parse(response.body()!!.info?.next)
            val nextPageQuery = uri.getQueryParameter("page")

            characterDao.insertAll(response.body()!!.results.map { it.toCharacter() })

            nextPageNumber = nextPageQuery?.toInt()!!

            MediatorResult.Success(endOfPaginationReached = response.body()!!.info?.next == null)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}