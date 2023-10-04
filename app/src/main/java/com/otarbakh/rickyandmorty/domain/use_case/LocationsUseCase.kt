package com.otarbakh.rickyandmorty.domain.use_case

import com.otarbakh.rickyandmorty.common.Resource
import com.otarbakh.rickyandmorty.data.model.characters.CharactersDto
import com.otarbakh.rickyandmorty.data.model.locations.LocationsDto
import com.otarbakh.rickyandmorty.domain.repository.RickAndMortyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

//class LocationsUseCase @Inject constructor(
//    private val rickAndMortyRepository: RickAndMortyRepository
//) {
//    operator fun invoke(): Flow<Resource<LocationsDto>> = channelFlow {
//        rickAndMortyRepository.getLocations().collectLatest {
//            when(it){
//                is Resource.Success-> {
//                    send(Resource.Success(it.data))
//                }
//                is Resource.Loading-> {
//                    send(Resource.Loading(it.loading))
//                }
//                is Resource.Error-> {
//                    send(Resource.Error(it.error))
//                }
//            }
//        }
//    }
//}