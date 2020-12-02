package com.sonat.movies.data.models

data class Movie(
    val title: String,
    val tags: Set<String>,
    val pgRating: Int,
    val reviewsCount: Int,
    val durationInMin: Int,
    val posterResId: Int,
    val storyline: String = "",
    val actors: List<Actor> = emptyList(),
    var isFavorite: Boolean = false,
)
