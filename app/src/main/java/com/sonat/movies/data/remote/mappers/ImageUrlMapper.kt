package com.sonat.movies.data.remote.mappers

import com.sonat.movies.data.EMPTY_STRING

object ImageUrlMapper {

    fun map(baseUrl: String, relativeUrl: String?, size: String) =
        if (relativeUrl.isNullOrEmpty()) EMPTY_STRING else "$baseUrl$size$relativeUrl"
}