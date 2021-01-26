package com.sonat.movies.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieListResponse(
    @SerialName("page") val page: Int,
    @SerialName("results") val movies: List<MovieDto>,
)
