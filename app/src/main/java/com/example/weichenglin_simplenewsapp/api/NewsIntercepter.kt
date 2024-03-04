package com.example.weichenglin_simplenewsapp.api

import android.util.Log
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

private const val API_KEY = "f229ef3749474f50b356e2f04a169a85"

class NewsInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest : Request = chain.request()

        val newUrl :HttpUrl = originalRequest.url.newBuilder()
            .addQueryParameter("apiKey", API_KEY)
            .build()

        val newRequest : Request = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        Log.d("Interceptor", newRequest.toString())
        return chain.proceed(newRequest)
    }
}