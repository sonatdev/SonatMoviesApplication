package com.sonat.movies.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDto(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("adult") val adult: Boolean,
    @SerialName("vote_average") val reviewsRating: Float,
    @SerialName("vote_count") val reviewsCount: Int,
    @SerialName("poster_path") val posterUrl: String?,
    @SerialName("genre_ids") val genres: List<Int>,
)