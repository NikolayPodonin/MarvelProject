package com.podonin.marvel_remote_impl.data.service

import com.podonin.marvel_remote_impl.data.modelRemote.MarvelCharactersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelCharactersService {
    @GET("/v1/public/characters")
    suspend fun characters(
        @Query("offset") offset: Int
    ): MarvelCharactersResponse
}