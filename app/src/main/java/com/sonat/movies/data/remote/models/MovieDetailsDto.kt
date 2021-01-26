package com.sonat.movies.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsDto(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("adult") val adult: Boolean,
    @SerialName("vote_average") val reviewsRating: Float,
    @SerialName("vote_count") val reviewsCount: Int,
    @SerialName("runtime") val durationInMin: Int,
    @SerialName("backdrop_path") val backdropUrl: String?,
    @SerialName("overview") val storyline: String?,
    @SerialName("genres") val genres: List<GenreDto>,
)
