package com.otarbakh.rickyandmorty.data.remotemediator

import android.net.Uri
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.otarbakh.rickyandmorty.data.database.dao.CharactersDao
import com.otarbakh.rickyandmorty.data.database.model.CharactersEntity
import com.otarbakh.rickyandmorty.data.model.characters.toCharacter
import com.otarbakh.rickyandmorty.data.service.RickyAndMortyService

@OptIn(ExperimentalPagingApi::class)
class CharactersRemoteMediator(
    private val apiService: RickyAndMortyService,
    private val charactersDao: CharactersDao,
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
            val uri = Uri.parse(response.body()!!.info?.next)
            val nextPageQuery = uri.getQueryParameter("page")
            charactersDao.insertAllCharacters(response.body()!!.results.map { it.toCharacter() })
            nextPageNumber = nextPageQuery?.toInt()!!
            MediatorResult.Success(endOfPaginationReached = response.body()!!.info?.next == null)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}