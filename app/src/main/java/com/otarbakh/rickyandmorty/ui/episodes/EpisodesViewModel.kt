package com.otarbakh.rickyandmorty.ui.episodes

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.otarbakh.rickyandmorty.common.BaseViewModel
import com.otarbakh.rickyandmorty.data.database.model.EpisodesEntity
import com.otarbakh.rickyandmorty.domain.repository.RickAndMortyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    private val rickAndMortyRepository: RickAndMortyRepository
) : BaseViewModel<PagingData<EpisodesEntity>>() {

    init {
        getEpisodes()
    }

    fun getEpisodes() {
        launchDataLoad {
            rickAndMortyRepository.getEpisodes().cachedIn(viewModelScope).first()
        }
    }
}