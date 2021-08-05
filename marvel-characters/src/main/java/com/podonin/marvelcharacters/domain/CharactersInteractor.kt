package com.podonin.marvelcharacters.domain

import com.podonin.marvel_remote_api.domain.MarvelRepository
import com.podonin.marvelcharacters.domain.model.SimpleCharacter
import com.podonin.marvelcharacters.domain.model.toSimple

class CharactersInteractor(private val repository: MarvelRepository) {

    suspend fun getCharacters(): List<SimpleCharacter> {
        return repository.getCharacters(0).toSimple()
    }

    suspend fun getNextCharacters(loadedCount: Int): List<SimpleCharacter> {
        return repository.getCharacters(loadedCount).toSimple()
    }
}
