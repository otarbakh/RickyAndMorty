package com.otarbakh.rickyandmorty.ui.singlecharacters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.otarbakh.rickyandmorty.common.Resource
import com.otarbakh.rickyandmorty.data.database.model.CharactersEntity
import com.otarbakh.rickyandmorty.data.model.characters.singlecharacter.SingleCharacterDto
import com.otarbakh.rickyandmorty.domain.repository.RickAndMortyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleCharacterViewModel @Inject constructor(
    private val rickAndMortyRepository: RickAndMortyRepository
) : ViewModel() {

    private val _state = MutableStateFlow<Resource<SingleCharacterDto>>(Resource.Loading(false))
    val state = _state.asStateFlow()


     fun getSingleCharacter(id: Int) {
        viewModelScope.launch {
            rickAndMortyRepository.getSingleCharacter(id).collectLatest {
                _state.value = it
            }

        }
    }

}
