package com.sonat.movies.domain

import android.content.Context
import com.sonat.movies.data.loadMovies
import com.sonat.movies.data.models.Movie

object MoviesDataSource {

    private val favoriteMovieIdList = mutableSetOf<Int>()

    suspend fun getMovies(context: Context) = loadMovies(context)
        .onEach { if (favoriteMovieIdList.contains(it.id)) it.isFavorite = true }

    suspend fun getMovieById(movieId: Int, context: Context) =
        getMovies(context).first { it.id == movieId }

    fun addMovieToFavorites(movie: Movie) {
        movie.isFavorite = !movie.isFavorite
        if (movie.isFavorite) {
            favoriteMovieIdList.add(movie.id)
        } else {
            favoriteMovieIdList.remove(movie.id)
        }
    }

}