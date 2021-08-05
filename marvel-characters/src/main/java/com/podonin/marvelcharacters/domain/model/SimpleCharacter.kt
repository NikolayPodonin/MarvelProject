package com.podonin.marvelcharacters.domain.model

import com.podonin.marvel_remote_api.domain.MarvelCharacter

data class SimpleCharacter(
    val id: String,
    val name: String,
    val description: String,
    val avatarUrl: String
)

fun MarvelCharacter.toSimple(): SimpleCharacter {
    return SimpleCharacter(
        id, name, description, avatarUrl
    )
}

fun List<MarvelCharacter>.toSimple(): List<SimpleCharacter> {
    return map { it.toSimple() }
}