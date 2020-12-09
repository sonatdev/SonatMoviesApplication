package com.sonat.movies.domain

import com.sonat.movies.data.models.Movie

class MoviesDataSource() {

    fun getMovies() = listOf<Movie>() //ToDo

    fun getMovieByTitle(title: String) = getMovies().first { it.title == title }
}