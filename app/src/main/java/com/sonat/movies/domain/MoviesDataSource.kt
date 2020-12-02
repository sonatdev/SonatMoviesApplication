package com.sonat.movies.domain

import com.sonat.movies.R
import com.sonat.movies.data.models.Actor
import com.sonat.movies.data.models.Movie

class MoviesDataSource {
    private val movies = listOf(*getUniqueMovies(), *getUniqueMovies())

    fun getMovies() = movies

    fun getMovieByTitle(title: String) = movies.filter { it.title == title }[0]

    private fun getUniqueMovies() = arrayOf(
        Movie(
            title = "Avengers: End Game",
            tags = setOf("Action", "Adventure", "Drama"),
            pgRating = 13,
            reviewsCount = 125,
            durationInMin = 137,
            posterResId = R.drawable.poster_avengers,
            actors = listOf(*getUniqueActors(), *getUniqueActors()),
            storyline = """After the devastating events of Avengers: Infinity War, the universe is in ruins. 
                            |With the help of remaining allies, the Avengers assemble once more in order to 
                            |reverse Thanos' actions and restore balance to the universe.""".trimMargin(),
        ),

        Movie(
            title = "Tenet",
            tags = setOf("Action", "Sci-Fi", "Thriller"),
            pgRating = 16,
            reviewsCount = 98,
            durationInMin = 97,
            posterResId = R.drawable.poster_tenet,
            isFavorite = true,
            actors = listOf(*getUniqueActors()),
            storyline = "Tenet Storyline: ToDo"
        ),

        Movie(
            title = "Black Widow",
            tags = setOf("Action", "Adventure", "Sci-Fi"),
            pgRating = 13,
            reviewsCount = 38,
            durationInMin = 102,
            posterResId = R.drawable.poster_black_widow,
            isFavorite = true,
            actors = listOf(*getUniqueActors()),
            storyline = "Black Widow Storyline: ToDo"
        ),

        Movie(
            title = "Wonder Woman 1984",
            tags = setOf("Action", "Adventure", "Fantasy"),
            pgRating = 13,
            reviewsCount = 74,
            durationInMin = 120,
            posterResId = R.drawable.poster_wonder_woman_1984,
            actors = listOf(*getUniqueActors(), *getUniqueActors()),
            storyline = "Wonder Woman 1984 Storyline: ToDo"
        ),
    )

    private fun getUniqueActors() = arrayOf(
        Actor("Robert Downey Jr.", R.drawable.actor_robert_downey_jr),
        Actor("Chris Evans", R.drawable.actor_chris_evans),
        Actor("Mark Ruffalo", R.drawable.actor_mark_ruffalo),
        Actor("Chris Hemsworth", R.drawable.actor_chris_hemsworth),
    )
}