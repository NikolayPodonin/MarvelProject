package com.podonin.marvel_details.domain

import com.podonin.marvel_details.domain.model.FullCharacter
import com.podonin.marvel_details.domain.model.mapToFull
import com.podonin.marvel_remote_api.domain.MarvelRepository

class DetailsInteractor(
    private val repository: MarvelRepository
) {

    suspend fun getCharacter(characterId: String): FullCharacter {
        return repository.getCharacter(characterId).mapToFull()
    }
}
