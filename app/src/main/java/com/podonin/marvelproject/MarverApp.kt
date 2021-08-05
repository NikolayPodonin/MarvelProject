package com.podonin.marvelproject

import android.app.Application
import com.podonin.marvel_details.di.getDetailsModules
import com.podonin.marvel_remote_impl.di.getApiModules
import com.podonin.marvelcharacters.di.getCharactersModules
import io.realm.Realm
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

        Realm.init(this)
    }
}