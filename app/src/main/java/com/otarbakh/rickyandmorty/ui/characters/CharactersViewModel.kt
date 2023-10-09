package com.otarbakh.rickyandmorty.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.otarbakh.rickyandmorty.common.BaseViewModel
import com.otarbakh.rickyandmorty.common.Resource
import com.otarbakh.rickyandmorty.data.database.model.CharactersEntity
import com.otarbakh.rickyandmorty.data.model.characters.CharactersResult
import com.otarbakh.rickyandmorty.domain.repository.RickAndMortyRepository


import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel

class CharactersViewModel @Inject constructor(
    private val rickAndMortyRepository: RickAndMortyRepository
) : BaseViewModel<PagingData<CharactersEntity>>() {

    val searchQuery = MutableStateFlow("")

    val searchResults: StateFlow<Resource<List<CharactersResult>>> = searchQuery
        .flatMapLatest { query ->
            rickAndMortyRepository.getSearchedCharacters(query)
        }.stateIn(viewModelScope, SharingStarted.Lazily, Resource.Loading(true))

    init {
        getCharacters()
    }

    fun getCharacters() {
        launchDataLoad {
            rickAndMortyRepository.getCharacters().cachedIn(viewModelScope).first()
        }
    }

    fun setSearchQuery(query: String) {
        searchQuery.value = query
    }
}



