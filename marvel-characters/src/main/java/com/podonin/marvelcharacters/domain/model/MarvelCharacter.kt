package com.podonin.marvelcharacters.domain.model

data class MarvelCharacter(
    val id: Int,
    val name: String,
    val description: String,
    val avatarUrl: String
)