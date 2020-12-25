package com.sonat.movies.domain

import com.sonat.movies.data.models.Movie

// нормально ли, что приходится для каждой операции создавать свой подкласс?
// с дженериками как-то у меня не получилось все это разрулить
sealed class MoviesResult {

    sealed class MoviesList {

        class Success(val data: List<Movie>) : MoviesResult.MoviesList()

        class Error(val errorMessage: String) : MoviesResult.MoviesList()
    }

    sealed class MovieDetails {

        class Success(val data: Movie) : MoviesResult.MovieDetails()

        class Error(val errorMessage: String) : MoviesResult.MovieDetails()
    }
}