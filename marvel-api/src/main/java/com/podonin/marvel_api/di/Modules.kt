package com.podonin.marvel_api.di

import com.google.gson.GsonBuilder
import com.podonin.marvel_api.R
import com.podonin.marvel_api.data.model.MarvelCharactersResponse
import com.podonin.marvel_api.data.service.MarvelCharactersService
import com.podonin.marvel_api.remote.MarvelApiDeserializer
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

fun getApiModules() = listOf(
    retrofitModule,
    serviceModule
)

private val retrofitModule: Module = module {
    single<Retrofit> {
        val gsonBuilder = GsonBuilder()
            .registerTypeAdapter(
                MarvelCharactersResponse::class.java,
                MarvelApiDeserializer()
            )
            .create()

        Retrofit.Builder()
            .client(OkHttpClient())
            .baseUrl(androidContext().getString(R.string.api_base_url))
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
            .build()
    }
}

private val serviceModule: Module = module {
    single<MarvelCharactersService> {
        get<Retrofit>().create()
    }
}
