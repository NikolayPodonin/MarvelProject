package com.podonin.marvelcharacters.domain

import com.podonin.marvelcharacters.domain.model.MarvelCharacter
import kotlinx.coroutines.delay

class CharactersInteractor {

    suspend fun getCharacters(): List<MarvelCharacter> {
        delay(2000)
        val charactersList = mutableListOf<MarvelCharacter>()
        for (i in 0..100) {
            charactersList.add(
                MarvelCharacter("name $i", "the description of $i", "")
            )
        }
        return charactersList
    }
}
