package com.otarbakh.rickyandmorty.ui.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.otarbakh.rickyandmorty.common.Resource
import com.otarbakh.rickyandmorty.data.database.model.EpisodesEntity
import com.otarbakh.rickyandmorty.data.model.episodes.EpisodesResult
import com.otarbakh.rickyandmorty.data.repository.EpisodesRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    private val episodesRepository: EpisodesRepositoryImpl,
) : ViewModel() {

    private val _state = MutableStateFlow<PagingData<EpisodesEntity>>(PagingData.empty())
    val state = _state.asStateFlow()

    fun getEpisodes() {
        viewModelScope.launch {
            episodesRepository.getEpisodes().cachedIn(viewModelScope).collectLatest {
                _state.value = it
            }
        }
    }

}