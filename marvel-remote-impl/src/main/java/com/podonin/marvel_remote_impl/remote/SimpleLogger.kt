package com.podonin.marvel_remote_impl.remote

import android.util.Log
import okhttp3.logging.HttpLoggingInterceptor

class SimpleLogger: HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        Log.d("OkHttp", message)
    }
}