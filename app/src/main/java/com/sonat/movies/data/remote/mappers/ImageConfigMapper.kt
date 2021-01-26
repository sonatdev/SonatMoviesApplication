package com.sonat.movies.data.remote.mappers

import com.sonat.movies.data.remote.models.ImageConfigDto
import com.sonat.movies.domain.ImageConfig

private const val IMAGE_SIZE_CONFIG_INDEX = 1

object ImageConfigMapper {

    fun map(imageConfig: ImageConfigDto) = ImageConfig(
        baseUrl = imageConfig.baseUrl,
        backdropSize = imageConfig.backdropSizes[IMAGE_SIZE_CONFIG_INDEX],
        logoSize = imageConfig.logoSizes[IMAGE_SIZE_CONFIG_INDEX],
        posterSize = imageConfig.posterSizes[IMAGE_SIZE_CONFIG_INDEX],
        profileSize = imageConfig.profileSizes[IMAGE_SIZE_CONFIG_INDEX],
    )

}