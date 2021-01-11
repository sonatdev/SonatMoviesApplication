package com.sonat.movies.domain

import android.content.Context
import com.sonat.movies.data.loadMovies
import com.sonat.movies.data.models.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class MoviesDataSource(val context: Context) {

    suspend fun getMovies() =
        withContext(Dispatchers.IO) {
            delay(500)

            try {
                val movies = loadMovies(context)
                    .onEach { if (favoriteMovieIdList.contains(it.id)) it.isFavorite = true }
                LoadResult.Success(movies)
            } catch (e: Exception) {
                LoadResult.Error(e.message.orEmpty())
            }
        }

    suspend fun getMovieById(movieId: Int) =
        withContext(Dispatchers.IO) {
            delay(500)

            try {
                when (val movieListResult = getMovies()) {
                    is LoadResult.Success -> {
                        val movie = movieListResult.data.first { it.id == movieId }
                        LoadResult.Success(movie)
                    }

                    is LoadResult.Error -> LoadResult.Error(movieListResult.errorMessage)
                }
            } catch (e: NoSuchElementException) {
                LoadResult.Error(e.message.orEmpty())
            }
        }

    fun addMovieToFavorites(movie: Movie) {
        movie.isFavorite = !movie.isFavorite
        if (movie.isFavorite) {
            favoriteMovieIdList.add(movie.id)
        } else {
            favoriteMovieIdList.remove(movie.id)
        }
    }

    private companion object {
        private val favoriteMovieIdList = mutableSetOf<Int>()
    }

}