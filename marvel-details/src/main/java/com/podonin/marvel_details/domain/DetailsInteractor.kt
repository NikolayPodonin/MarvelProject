package com.podonin.marvel_details.domain

import com.podonin.marvel_details.domain.model.FullCharacter
import kotlinx.coroutines.delay

class DetailsInteractor {

    suspend fun getCharacter(characterId: String): FullCharacter {
        return tempMethodLoader(characterId)
    }

    private suspend fun tempMethodLoader(characterId: String): FullCharacter {
        delay(2000)

        return FullCharacter(
            id = characterId,
            name = characterId,
            description = "description of $characterId",
            avatarUrl = "",
            comics = listOf("comics","comics","comics","comics","comics","comics","comics","comics","comics","comics","comics","comics","comics"),
            stories = listOf("stories"),
            events = listOf("events"),
            series = listOf("series")
        )
    }
}
