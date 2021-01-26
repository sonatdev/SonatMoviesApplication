package com.sonat.movies.data.remote.mappers

import com.sonat.movies.data.models.Genre
import com.sonat.movies.data.remote.models.GenreDto
import com.sonat.movies.data.remote.models.GenreListResponse

object GenreMapper {

    fun map(genresDto: List<GenreDto>) = genresDto.map { map(it) }

    fun map(genreListResponse: GenreListResponse): Map<Int, Genre> =
        genreListResponse.genres.associateByTo(mutableMapOf(), { it.id }, { map(it) })

    private fun map(genreDto: GenreDto) = Genre(
        id = genreDto.id,
        name = genreDto.name
    )

    fun map(genreIds: List<Int>, cachedGenres: Map<Int, Genre>) =
        genreIds.map { requireNotNull(cachedGenres[it]) }
}