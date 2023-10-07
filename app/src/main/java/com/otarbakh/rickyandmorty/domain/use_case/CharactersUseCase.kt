package com.otarbakh.rickyandmorty.domain.use_case

import com.otarbakh.rickyandmorty.common.Resource
import com.otarbakh.rickyandmorty.data.model.characters.singlecharacter.SingleCharacterDto
import com.otarbakh.rickyandmorty.domain.repository.RickAndMortyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharactersUseCase @Inject constructor(
    private val rickAndMortyRepository: RickAndMortyRepository
) {

    suspend fun getSingleCharacter(id: Int): Flow<Resource<SingleCharacterDto>> {
        return rickAndMortyRepository.getSingleCharacter(id)
    }

}