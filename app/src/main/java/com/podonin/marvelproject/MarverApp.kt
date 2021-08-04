package com.podonin.marvelproject

import android.app.Application
import com.podonin.marvel_api.di.getApiModules
import com.podonin.marvel_details.di.getDetailsModules
import com.podonin.marvelcharacters.di.getCharactersModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MarverApp : Application() {

    @ExperimentalCoroutinesApi
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MarverApp)
            modules(
                modules = getCharactersModules()
                        + getDetailsModules()
                        + getApiModules()
            )
        }
    }
}