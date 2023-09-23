package com.otarbakh.rickyandmorty.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.otarbakh.rickyandmorty.common.Resource
import com.otarbakh.rickyandmorty.data.model.characters.CharactersDto
import com.otarbakh.rickyandmorty.data.model.characters.Result
import com.otarbakh.rickyandmorty.domain.repository.RickAndMortyRepository


import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val rickAndMortyRepository: RickAndMortyRepository
) : ViewModel() {
    private val _state = MutableStateFlow<PagingData<Result>>(PagingData.empty())
    val state = _state.asStateFlow()


    suspend fun getCharacters() {
        viewModelScope.launch {
            rickAndMortyRepository.getCharacters().cachedIn(viewModelScope).collectLatest {
                _state.value = it
            }
        }

    }
}