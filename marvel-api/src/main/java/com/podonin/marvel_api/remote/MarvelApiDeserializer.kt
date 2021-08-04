package com.podonin.marvel_api.remote

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.podonin.marvel_api.data.model.MarvelCharactersResponse
import java.lang.reflect.Type

class MarvelApiDeserializer : JsonDeserializer<MarvelCharactersResponse> {

    private val gson = Gson()

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?,
    ): MarvelCharactersResponse? {
        json?.asJsonObject?.apply {
            return gson.fromJson(get("data"), MarvelCharactersResponse::class.java)
        }
        return null
    }

}