package com.sonat.movies.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sonat.movies.BuildConfig
import com.sonat.movies.data.remote.TheMovieDbApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit

object RetrofitModule {

    private val jsonConfig = Json {
        ignoreUnknownKeys = true
    }

    private val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        setLevel(if (BuildConfig.DEBUG) Level.BODY else Level.NONE)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(ApiKeyInterceptor())
        .addInterceptor(httpLoggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.TMDB_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(jsonConfig.asConverterFactory("application/json".toMediaType()))
        .build()

    val moviesApi = retrofit.create(TheMovieDbApiService::class.java)

}