package com.podonin.marvel_remote_impl.di

import com.google.gson.GsonBuilder
import com.podonin.marvel_remote_api.domain.MarvelRepository
import com.podonin.marvel_remote_impl.BuildConfig
import com.podonin.marvel_remote_impl.data.modelRemote.MarvelCharactersResponse
import com.podonin.marvel_remote_impl.data.repository.MarvelRepositoryImpl
import com.podonin.marvel_remote_impl.data.service.MarvelCharactersService
import com.podonin.marvel_remote_impl.remote.AuthInterceptor
import com.podonin.marvel_remote_impl.remote.MarvelApiDeserializer
import com.podonin.marvel_remote_impl.remote.SimpleLogger
import io.realm.Realm
import io.realm.RealmConfiguration
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

fun getApiModules() = listOf(
    retrofitModule,
    serviceModule,
    repositoryModule,
    realmModule
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
            .client(get())
            .baseUrl(BuildConfig.MARVEL_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
            .build()
    }

    single {
        OkHttpClient.Builder().apply {
            connectTimeout(10L, TimeUnit.SECONDS)
            writeTimeout(10L, TimeUnit.SECONDS)
            readTimeout(0L, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
            addInterceptor(AuthInterceptor())
            if (BuildConfig.DEBUG) addInterceptor(
                HttpLoggingInterceptor(SimpleLogger())
            )
        }.build()
    }
}

private val serviceModule: Module = module {
    single<MarvelCharactersService> {
        get<Retrofit>().create()
    }
}

private val repositoryModule: Module = module {
    single<MarvelRepository> { MarvelRepositoryImpl(get(), get()) }
}

private val realmModule: Module = module {
    single<Realm> {
        val realmName: String = "Marvel App Project"
        val config = RealmConfiguration.Builder()
            .name(realmName)
            .allowWritesOnUiThread(true)
            .allowQueriesOnUiThread(true)
            .build()
        Realm.getInstance(config)
    }
}
