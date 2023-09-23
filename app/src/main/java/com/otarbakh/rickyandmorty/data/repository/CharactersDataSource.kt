package com.otarbakh.rickyandmorty.data.repository

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.otarbakh.rickyandmorty.common.Constants.STARTING_PAGE_INDEX
import com.otarbakh.rickyandmorty.data.model.characters.Result
import com.otarbakh.rickyandmorty.data.service.RickyAndMortyService
import javax.inject.Inject

class CharactersDataSource @Inject constructor(
    private val rickyAndMortyService: RickyAndMortyService,
) : PagingSource<Int, Result>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val pageNumber = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response =
                rickyAndMortyService.fetchCharacters(
                    page = pageNumber,
                )

            val data = response.body()

            var nextPageNumber: Int? = null

            if (data!!.info!!.next != null) {
                val uri = Uri.parse(data.info!!.next)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery?.toInt()
            }

            LoadResult.Page(
                data = data.results,
                prevKey = if (pageNumber == STARTING_PAGE_INDEX) null else pageNumber - 1,
                nextKey = nextPageNumber
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return null
    }


}