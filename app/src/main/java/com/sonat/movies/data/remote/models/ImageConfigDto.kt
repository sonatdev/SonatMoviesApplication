package com.sonat.movies.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageConfigDto(
    @SerialName("secure_base_url") val baseUrl: String,
    @SerialName("backdrop_sizes") val backdropSizes: List<String>,
    @SerialName("logo_sizes") val logoSizes: List<String>,
    @SerialName("poster_sizes") val posterSizes: List<String>,
    @SerialName("profile_sizes") val profileSizes: List<String>,
)
