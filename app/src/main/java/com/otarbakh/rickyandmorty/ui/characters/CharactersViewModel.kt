package com.otarbakh.rickyandmorty.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otarbakh.rickyandmorty.common.Resource
import com.otarbakh.rickyandmorty.data.model.characters.CharactersDto
import com.otarbakh.rickyandmorty.domain.use_case.CharactersUseCase
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltAndroidApp
class CharactersViewModel @Inject constructor(
    private val characterUseCase: CharactersUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<Resource<CharactersDto>>(Resource.Loading(false))
    val state = _state.asStateFlow()


    fun getCharacters() {
        characterUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> _state.value = Resource.Success(result.data)
                is Resource.Error -> _state.value = Resource.Error("woops!")
                is Resource.Loading -> _state.value = Resource.Loading(true)
            }

        }.launchIn(viewModelScope)
    }
}