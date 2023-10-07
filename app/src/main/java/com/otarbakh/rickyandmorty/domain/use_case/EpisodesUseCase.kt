package com.otarbakh.rickyandmorty.domain.use_case

import com.otarbakh.rickyandmorty.common.Resource
import com.otarbakh.rickyandmorty.data.model.episodes.multipleepisode.MultipleEpisodesDto
import com.otarbakh.rickyandmorty.domain.repository.RickAndMortyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EpisodesUseCase @Inject constructor(
    private val rickAndMortyRepository: RickAndMortyRepository
) {

    suspend fun getMultipleEpisodes(ids: List<Int>): Flow<Resource<MultipleEpisodesDto>> {
        return rickAndMortyRepository.getMultipleEpisodes(ids)
    }


}