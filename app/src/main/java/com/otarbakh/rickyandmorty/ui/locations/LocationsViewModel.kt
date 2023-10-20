package com.otarbakh.rickyandmorty.ui.locations


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.otarbakh.rickyandmorty.data.database.model.LocationsEntity
import com.otarbakh.rickyandmorty.domain.repository.EpisodesRepository
import com.otarbakh.rickyandmorty.domain.repository.LocationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val locationsRepository: LocationsRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<PagingData<LocationsEntity>>(PagingData.empty())
    val state = _state.asStateFlow()
    fun getLocations() {
        viewModelScope.launch {
            locationsRepository.getLocations().cachedIn(viewModelScope).collectLatest {
                _state.value = it
            }
        }
    }

}