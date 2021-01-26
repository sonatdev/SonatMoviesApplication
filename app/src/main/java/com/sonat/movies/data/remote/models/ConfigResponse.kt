package com.sonat.movies.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfigResponse(
    @SerialName("images") val imageConfig: ImageConfigDto
)