package com.sonat.movies.data.remote.mappers

import com.sonat.movies.data.models.Actor
import com.sonat.movies.data.remote.models.ActorDto
import com.sonat.movies.domain.ImageConfig

private const val ACTING_TYPE = "Acting"

object ActorMapper {

    fun map(actors: List<ActorDto>, imageConfig: ImageConfig) = actors
        .filter { it.activity == ACTING_TYPE }
        .map {
            Actor(
                id = it.id,
                name = it.name,
                photoUrl = ImageUrlMapper.map(
                    imageConfig.baseUrl,
                    it.photoUrl,
                    imageConfig.profileSize
                )
            )
        }

}