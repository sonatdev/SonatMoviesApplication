package com.sonat.movies.domain

import android.content.Context
import com.sonat.movies.data.loadMovies

class MoviesDataSource(private val context: Context) {

    suspend fun getMovies() = loadMovies(context)

    suspend fun getMovieById(movieId: Int) = getMovies().first { it.id == movieId }
}