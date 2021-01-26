package com.sonat.movies.data.remote.mappers

import com.sonat.movies.data.ADULT_AGE
import com.sonat.movies.data.models.Genre
import com.sonat.movies.data.models.Movie
import com.sonat.movies.data.remote.models.MovieDto
import com.sonat.movies.domain.ImageConfig

object MovieMapper {

    fun map(
        movie: MovieDto,
        genres: List<Genre>,
        imageConfig: ImageConfig,
        isFavorite: Boolean = false,
    ) =
        Movie(
            id = movie.id,
            title = movie.title,
            pgRating = mapPgRating(movie.adult),
            reviewsRating = movie.reviewsRating / 2,
            reviewsCount = movie.reviewsCount,
            durationInMin = 999, //ToDo: real value should be used
            posterUrl = ImageUrlMapper.map(
                imageConfig.baseUrl,
                movie.posterUrl,
                imageConfig.posterSize
            ),
            genres = genres,
            isFavorite = isFavorite,
        )

    private fun mapPgRating(adult: Boolean) = if (adult) ADULT_AGE else 0

}