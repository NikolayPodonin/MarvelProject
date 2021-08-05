package com.podonin.marvel_remote_api.domain

interface MarvelRepository {
    suspend fun getCharacters(offset: Int, nameStartsWith: String? = null): List<MarvelCharacter>
    suspend fun getCharacter(characterId: String): MarvelCharacter
}