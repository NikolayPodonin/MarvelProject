package com.podonin.marvelcharacters.domain

import android.util.Log
import com.podonin.marvel_api.data.service.MarvelCharactersService
import com.podonin.marvelcharacters.domain.model.SimpleCharacter
import kotlinx.coroutines.delay

class CharactersInteractor(private val service: MarvelCharactersService) {

    suspend fun getCharacters(): List<SimpleCharacter> {
        val chars = service.characters("8f167851ff88f7c9d511c9dad2fe0916")
        Log.d("AAAA", chars.toString())
        return tempMethodLoader(0)
    }

    suspend fun getNextCharacters(loadedCount: Int): List<SimpleCharacter> {
        return tempMethodLoader(loadedCount)
    }

    private suspend fun tempMethodLoader(startPoint: Int): MutableList<SimpleCharacter> {
        delay(2000)
        val charactersList = mutableListOf<SimpleCharacter>()
        for (i in startPoint..startPoint + 100) {
            charactersList.add(
                SimpleCharacter("i", "name $i", "the description of $i", "")
            )
        }
        return charactersList
    }
}
