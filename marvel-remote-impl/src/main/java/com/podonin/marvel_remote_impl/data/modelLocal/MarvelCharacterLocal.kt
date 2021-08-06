package com.podonin.marvel_remote_impl.data.modelLocal

import com.podonin.marvel_remote_api.domain.MarvelCharacter
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class MarvelCharacterLocal : RealmObject() {
    @PrimaryKey
    var id: String = ""

    @Required
    var name: String = ""
    var description: String = ""
    var avatarUrl: String = ""
    var comics: RealmList<String> = RealmList()
    var stories: RealmList<String> = RealmList()
    var events: RealmList<String> = RealmList()
    var series: RealmList<String> = RealmList()
}


fun MarvelCharacterLocal.mapToDomain(): MarvelCharacter {
    return MarvelCharacter(
        id, name, description, avatarUrl, comics, stories, events, series
    )
}

fun MarvelCharacter.mapToLocal(): MarvelCharacterLocal {
    return MarvelCharacterLocal().also {
        it.id = id
        it.name = name
        it.description = description
        it.avatarUrl = avatarUrl
        it.comics = RealmList<String>().apply {
            addAll(comics)
        }
        it.stories = RealmList<String>().apply {
            addAll(stories)
        }
        it.events = RealmList<String>().apply {
            addAll(events)
        }
        it.series = RealmList<String>().apply {
            addAll(series)
        }
    }
}

fun List<MarvelCharacter>.mapToLocal(): List<MarvelCharacterLocal> {
    return map { it.mapToLocal() }
}

fun List<MarvelCharacterLocal>.mapToDomain(): List<MarvelCharacter> {
    return map { it.mapToDomain() }
}