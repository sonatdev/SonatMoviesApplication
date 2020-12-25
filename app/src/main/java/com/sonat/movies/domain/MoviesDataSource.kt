package com.sonat.movies.domain

import android.content.Context
import com.sonat.movies.data.loadMovies
import com.sonat.movies.data.models.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

object MoviesDataSource {

    private val favoriteMovieIdList = mutableSetOf<Int>()

    suspend fun getMovies(context: Context) =
        withContext(Dispatchers.IO) {
            delay(500)

            try {
                val movies = loadMovies(context)
                    .onEach { if (favoriteMovieIdList.contains(it.id)) it.isFavorite = true }
                MoviesResult.MoviesList.Success(movies)
            } catch (e: Exception) {
                MoviesResult.MoviesList.Error(e.message.orEmpty())
            }
        }

    suspend fun getMovieById(movieId: Int, context: Context) =
        withContext(Dispatchers.IO) {
            delay(500)

            try {
                when (val movieListResult = getMovies(context)) {
                    is MoviesResult.MoviesList.Success -> {
                        val movie = movieListResult.data.first { it.id == movieId }
                        MoviesResult.MovieDetails.Success(movie)
                    }

                    is MoviesResult.MoviesList.Error -> MoviesResult.MovieDetails.Error(
                        movieListResult.errorMessage
                    )
                }
            } catch (e: NoSuchElementException) {
                MoviesResult.MovieDetails.Error(e.message.orEmpty())
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

    // ToDo: temporal solution, DI is needed
    fun getInstance() = this

}