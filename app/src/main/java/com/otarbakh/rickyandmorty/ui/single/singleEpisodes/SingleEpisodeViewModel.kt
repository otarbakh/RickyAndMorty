package com.otarbakh.rickyandmorty.ui.single.singlecharacters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otarbakh.rickyandmorty.common.Resource
import com.otarbakh.rickyandmorty.data.model.episodes.singleepisode.SingleEpisodeDto
import com.otarbakh.rickyandmorty.data.model.locations.mutliplecharacters.MultipleCharactersDto
import com.otarbakh.rickyandmorty.data.repository.EpisodesRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleEpisodeViewModel @Inject constructor(
    private val repositoryImpl: EpisodesRepositoryImpl
) : ViewModel() {

    private val _characterState =
        MutableStateFlow<Resource<MultipleCharactersDto>>(Resource.Loading(false))
    val characterState = _characterState.asStateFlow()

    private val _state = MutableStateFlow<Resource<SingleEpisodeDto>>(Resource.Loading(false))
    val state = _state.asStateFlow()


    fun getSingleEpisode(id: Int) {
        viewModelScope.launch {
            repositoryImpl.getSingleEpisode(id).collectLatest {
                _state.value = it
            }
        }
    }

    fun getMultipleCharacters(ids: List<Int>) {
        viewModelScope.launch {
            repositoryImpl.getMultipleCharacters(ids).collectLatest {
                _characterState.value = it
            }
        }
    }
}

