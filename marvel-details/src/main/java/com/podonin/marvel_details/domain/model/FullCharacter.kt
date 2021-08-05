package com.podonin.marvel_details.domain.model

import com.podonin.marvel_remote_api.domain.MarvelCharacter

data class FullCharacter(
    val id: String,
    val name: String,
    val description: String,
    val avatarUrl: String,
    val comics: List<String>,
    val stories: List<String>,
    val events: List<String>,
    val series: List<String>
)

fun MarvelCharacter.mapToFull(): FullCharacter {
    return FullCharacter(
        id, name, description, avatarUrl, comics, stories, events, series
    )
}