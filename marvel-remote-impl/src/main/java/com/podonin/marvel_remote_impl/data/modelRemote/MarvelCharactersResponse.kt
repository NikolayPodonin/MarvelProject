package com.podonin.marvel_remote_impl.data.modelRemote

import com.google.gson.annotations.SerializedName
import com.podonin.marvel_remote_api.domain.MarvelCharacter

data class MarvelCharactersResponse(
    @SerializedName("offset") val offset: Int,
    @SerializedName("limit") val limit: Int,
    @SerializedName("total") val total: Int,
    @SerializedName("count") val count: Int,
    @SerializedName("results") val results: List<MarvelCharacterRemote>
) {
    fun mapToDomain(): List<MarvelCharacter> {
        return results.map { data ->
            MarvelCharacter(
                id = data.id,
                name = data.name,
                description = data.description,
                avatarUrl = "${data.thumbnail.path}.${data.thumbnail.extension}",
                comics = data.comics.items.map { it.name },
                stories = data.stories.items.map { it.name },
                events = data.events.items.map { it.name },
                series = data.series.items.map { it.name }
            )
        }
    }
}