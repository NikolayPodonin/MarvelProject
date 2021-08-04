package com.podonin.marvelcharacters.domain.model

data class SimpleCharacter(
    val id: String,
    val name: String,
    val description: String,
    val avatarUrl: String
)