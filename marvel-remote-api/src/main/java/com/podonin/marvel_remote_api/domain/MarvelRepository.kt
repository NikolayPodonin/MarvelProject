package com.podonin.marvel_remote_api.domain

interface MarvelRepository {
    suspend fun getCharacters(offset: Int): List<MarvelCharacter>
    suspend fun getCharacter(characterId: String): MarvelCharacter
}