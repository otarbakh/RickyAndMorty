package com.otarbakh.rickyandmorty.ui.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otarbakh.rickyandmorty.common.Resource
import com.otarbakh.rickyandmorty.data.model.characters.CharactersDto
import com.otarbakh.rickyandmorty.data.model.locations.LocationsDto


import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

//@HiltViewModel
//class LocationsViewModel @Inject constructor(
//    private val locationsUseCase: LocationsUseCase
//) : ViewModel() {
//    private val _state = MutableStateFlow<Resource<LocationsDto>>(Resource.Loading(false))
//    val state = _state.asStateFlow()
//
//
//    fun getLocations() {
//        locationsUseCase().onEach { result ->
//            when (result) {
//                is Resource.Success -> _state.value = Resource.Success(result.data)
//                is Resource.Error -> _state.value = Resource.Error("woops!")
//                is Resource.Loading -> _state.value = Resource.Loading(true)
//            }
//
//        }.launchIn(viewModelScope)
//    }
//}