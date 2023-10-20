package com.otarbakh.rickyandmorty.domain.use_case

import com.otarbakh.rickyandmorty.common.Resource
import com.otarbakh.rickyandmorty.data.model.characters.singlecharacter.SingleCharacterDto
import com.otarbakh.rickyandmorty.domain.repository.CharactersRepository
import com.otarbakh.rickyandmorty.domain.repository.EpisodesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharactersUseCase @Inject constructor(
    private val episodesRepository: CharactersRepository
) {
    suspend fun getSingleCharacter(id: Int): Flow<Resource<SingleCharacterDto>> {
        return episodesRepository.getSingleCharacter(id)
    }

}