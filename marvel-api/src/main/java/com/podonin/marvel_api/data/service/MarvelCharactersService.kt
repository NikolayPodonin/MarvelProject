package com.podonin.marvel_api.data.service

import com.podonin.marvel_api.data.model.MarvelCharactersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelCharactersService {
    @GET("/v1/public/characters")
    suspend fun characters(
        @Query("apikey") apikey: String
    ): MarvelCharactersResponse
}