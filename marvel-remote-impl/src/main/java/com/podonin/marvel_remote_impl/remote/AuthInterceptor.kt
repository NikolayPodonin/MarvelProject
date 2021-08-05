package com.podonin.marvel_remote_impl.remote

import com.podonin.marvel_remote_impl.BuildConfig
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val originalHttpUrl: HttpUrl = original.url

        val ts = Date().time.toString()
        val apiKey = BuildConfig.MARVEL_API_PUBLIC_KEY
        val privateKey = BuildConfig.MARVEL_API_PRIVATE_KEY

        val hash = hashMD5("$ts$privateKey$apiKey")

        val url: HttpUrl = originalHttpUrl.newBuilder()
            .addQueryParameter("ts", ts)
            .addQueryParameter("apikey", apiKey)
            .addQueryParameter("hash", hash)
            .build()

        // Request customization: add request headers

        // Request customization: add request headers
        val requestBuilder: Request.Builder = original.newBuilder()
            .url(url)

        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }

    private fun hashMD5(input: String): String {

        val md = MessageDigest.getInstance("MD5")

        return BigInteger(
            1,
            md.digest(input.toByteArray())
        ).toString(16).padStart(32, '0')
    }
}