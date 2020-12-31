package com.sonat.movies.data.models

data class Movie(
    val id: Int,
    val title: String,
    val pgRating: Int,
    val reviewsRating: Float,
    val reviewsCount: Int,
    val durationInMin: Int,
    val posterUrl: String,
    val backdropUrl: String,
    val storyline: String,
    val genres: List<Genre>,
    val actors: List<Actor> = emptyList(),
    var isFavorite: Boolean = false,
)
