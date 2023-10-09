package com.otarbakh.rickyandmorty.ui.locations


import androidx.paging.PagingData
import com.otarbakh.rickyandmorty.common.BaseViewModel
import com.otarbakh.rickyandmorty.data.database.model.LocationsEntity
import com.otarbakh.rickyandmorty.domain.repository.RickAndMortyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val rickAndMortyRepository: RickAndMortyRepository
) : BaseViewModel<PagingData<LocationsEntity>>() {

    init {
        getLocations()
    }

    fun getLocations() {
        launchDataLoad {
            rickAndMortyRepository.getLocations().first()
        }
    }
}