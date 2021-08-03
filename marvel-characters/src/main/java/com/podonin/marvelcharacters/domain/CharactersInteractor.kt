package com.podonin.marvelcharacters.domain

import com.podonin.marvelcharacters.domain.model.MarvelCharacter
import kotlinx.coroutines.delay

class CharactersInteractor {

    suspend fun getCharacters(): List<MarvelCharacter> {
        return tempMethodLoader(0)
    }


    suspend fun getNextCharacters(loadedCount: Int): List<MarvelCharacter> {
        return tempMethodLoader(loadedCount)
    }

    private suspend fun tempMethodLoader(startPoint: Int): MutableList<MarvelCharacter> {
        delay(2000)
        val charactersList = mutableListOf<MarvelCharacter>()
        for (i in startPoint..startPoint + 100) {
            charactersList.add(
                MarvelCharacter(i, "name $i", "the description of $i", "")
            )
        }
        return charactersList
    }
}
