package com.sonat.movies.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CastResponse(
    @SerialName("id") val movieId: Int,
    @SerialName("cast") val actors: List<ActorDto>,
)