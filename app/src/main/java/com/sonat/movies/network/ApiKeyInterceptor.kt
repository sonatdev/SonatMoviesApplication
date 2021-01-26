package com.sonat.movies.network

import com.sonat.movies.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

private const val API_KEY_QUERY_PARAM = "api_key"

class ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val modifiedUrlWithApiKey = originalRequest.url.newBuilder()
            .addQueryParameter(API_KEY_QUERY_PARAM, BuildConfig.TMDB_API_KEY)
            .build()

        val modifiedRequest = originalRequest.newBuilder()
            .url(modifiedUrlWithApiKey)
            .build()

        return chain.proceed(modifiedRequest)
    }
}