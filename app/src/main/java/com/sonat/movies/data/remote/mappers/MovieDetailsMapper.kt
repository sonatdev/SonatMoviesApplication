package com.sonat.movies.data.remote.mappers

import com.sonat.movies.data.ADULT_AGE
import com.sonat.movies.data.EMPTY_STRING
import com.sonat.movies.data.models.Movie
import com.sonat.movies.data.remote.models.ActorDto
import com.sonat.movies.data.remote.models.MovieDetailsDto
import com.sonat.movies.domain.ImageConfig

object MovieDetailsMapper {

    fun map(
        movieDetails: MovieDetailsDto,
        actors: List<ActorDto>,
        imageConfig: ImageConfig,
        isFavorite: Boolean = false,
    ) =
        Movie(
            id = movieDetails.id,
            title = movieDetails.title,
            pgRating = mapPgRating(movieDetails.adult),
            reviewsRating = movieDetails.reviewsRating / 2,
            reviewsCount = movieDetails.reviewsCount,
            durationInMin = movieDetails.durationInMin,
            backdropUrl = ImageUrlMapper.map(
                imageConfig.baseUrl,
                movieDetails.backdropUrl,
                imageConfig.backdropSize
            ),
            storyline = movieDetails.storyline ?: EMPTY_STRING,
            genres = GenreMapper.map(movieDetails.genres),
            actors = ActorMapper.map(actors, imageConfig),
            isFavorite = isFavorite,
        )

    private fun mapPgRating(adult: Boolean) = if (adult) ADULT_AGE else 0

}