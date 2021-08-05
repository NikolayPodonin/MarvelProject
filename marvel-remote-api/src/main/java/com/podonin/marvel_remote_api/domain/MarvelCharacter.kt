package com.podonin.marvel_remote_api.domain

data class MarvelCharacter(
    val id: String,
    val name: String,
    val description: String,
    val avatarUrl: String,
    val comics: List<String>,
    val stories: List<String>,
    val events: List<String>,
    val series: List<String>
)