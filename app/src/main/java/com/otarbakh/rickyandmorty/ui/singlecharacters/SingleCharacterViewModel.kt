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

    private val _state = MutableStateFlow<Resource<SingleCharacterDto>>(Resource.Loading(true))
    val state = _state.asStateFlow()

    private val _episodeState = MutableStateFlow<Resource<List<String?>?>>(Resource.Loading(true))
    val episodeState = _episodeState.asStateFlow()



    suspend fun getSingleCharacterEpisodes(id:Int) {
        viewModelScope.launch {
            rickAndMortyRepository.getSingleCharacter(id).collectLatest {
                when (it){
                    is Resource.Success -> _episodeState.value = Resource.Success(it.data.episode)
                    is Resource.Error -> _episodeState.value = Resource.Error("woops!")
                    is Resource.Loading -> _episodeState.value = Resource.Loading(true)
                }
            }

        }
    }


    suspend fun getSingleCharacter(id:Int) {
        viewModelScope.launch {
            rickAndMortyRepository.getSingleCharacter(id).collectLatest {
                when (it){
                    is Resource.Success -> _state.value = Resource.Success(it.data)
                    is Resource.Error -> _state.value = Resource.Error("woops!")
                    is Resource.Loading -> _state.value = Resource.Loading(true)
                }
            }

        }
    }

}
