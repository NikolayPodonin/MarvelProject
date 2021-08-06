package com.podonin.marvel_remote_impl.data.repository

import com.podonin.marvel_remote_api.domain.MarvelCharacter
import com.podonin.marvel_remote_api.domain.MarvelRepository
import com.podonin.marvel_remote_impl.data.modelLocal.MarvelCharacterLocal
import com.podonin.marvel_remote_impl.data.modelLocal.mapToDomain
import com.podonin.marvel_remote_impl.data.modelLocal.mapToLocal
import com.podonin.marvel_remote_impl.data.service.MarvelCharactersService
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext


/**
 * This class is deliberately simplified.
 * Now we think that characters never change on server,
 * so we never compare previously saved cache with server data
 * and never delete it.
 * */
class MarvelRepositoryImpl(
    private val charactersService: MarvelCharactersService,
    private val realm: Realm
) : MarvelRepository {

    override suspend fun getCharacter(characterId: String): MarvelCharacter {
        return realm.where<MarvelCharacterLocal>()
            .equalTo("id", characterId)
            .findFirst()
            ?.mapToDomain() ?: throw IllegalStateException("No such character")
    }

    override suspend fun getCharacters(offset: Int): List<MarvelCharacter> {
        val locals = realm.where<MarvelCharacterLocal>()
            .findAll()
            .toList()
        if (offset < locals.size) return locals.mapToDomain()

        val characters = charactersService.characters(offset).mapToDomain()
        saveToDatabase(characters)
        return characters
    }

    private suspend fun saveToDatabase(characters: List<MarvelCharacter>) =
        CoroutineScope(coroutineContext).launch {
            realm.executeTransaction {
                it.copyToRealmOrUpdate(characters.mapToLocal())
            }
        }
}