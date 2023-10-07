package com.otarbakh.rickyandmorty.ui.singlecharacters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otarbakh.rickyandmorty.common.Resource
import com.otarbakh.rickyandmorty.data.model.characters.CharactersDto
import com.otarbakh.rickyandmorty.data.model.characters.singlecharacter.SingleCharacterDto
import com.otarbakh.rickyandmorty.data.model.episodes.multipleepisode.MultipleEpisodesDto
import com.otarbakh.rickyandmorty.domain.use_case.CharactersUseCase
import com.otarbakh.rickyandmorty.domain.use_case.EpisodesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleCharacterViewModel @Inject constructor(
    private val charactersUseCase: CharactersUseCase,
    private val episodesUseCase: EpisodesUseCase
) : ViewModel() {

    private val _episodesState =
        MutableStateFlow<Resource<MultipleEpisodesDto>>(Resource.Loading(false))
    val episodesState = _episodesState.asStateFlow()

    private val _state = MutableStateFlow<Resource<SingleCharacterDto>>(Resource.Loading(false))
    val state = _state.asStateFlow()


    fun getSingleCharacter(id: Int) {
        viewModelScope.launch {
            charactersUseCase.getSingleCharacter(id).collectLatest {
                _state.value = it
            }

        }
    }

    fun getMultipleEpisodes(ids: List<Int>) {
        viewModelScope.launch {
            episodesUseCase.getMultipleEpisodes(ids).collectLatest {
                _episodesState.value = it
            }

        }
    }



}

