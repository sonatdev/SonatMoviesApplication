package com.sonat.movies.domain

import com.sonat.movies.data.models.Genre
import com.sonat.movies.data.models.Movie
import com.sonat.movies.data.remote.TheMovieDbApiService
import com.sonat.movies.data.remote.mappers.GenreMapper
import com.sonat.movies.data.remote.mappers.ImageConfigMapper
import com.sonat.movies.data.remote.mappers.MovieDetailsMapper
import com.sonat.movies.data.remote.mappers.MovieMapper
import com.sonat.movies.network.RetrofitModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class MoviesDataSource private constructor(
    private val moviesApi: TheMovieDbApiService,
) {
    private var cachedImageConfig: ImageConfig? = null
    private var cachedGenresMap: Map<Int, Genre> = emptyMap()

    suspend fun getMovies() =
        withContext(Dispatchers.IO) {
            try {
                val movies = moviesApi.getPopularMovies().movies.map { movie ->
                    MovieMapper.map(
                        movie = movie,
                        genres = GenreMapper.map(movie.genres, getAllGenres()),
                        imageConfig = getImageConfigs(),
                        isFavorite = favoriteMovieIdList.contains(movie.id),
                    )
                }

                LoadResult.Success(movies)
            } catch (e: Exception) {
                LoadResult.Error(e.message.orEmpty())
            }
        }

    private suspend fun getAllGenres(): Map<Int, Genre> {
        if (cachedGenresMap.isEmpty()) {
            cachedGenresMap = GenreMapper.map(moviesApi.getAllGenres())
        }

        return cachedGenresMap
    }

    suspend fun getMovieById(movieId: Int) =
        withContext(Dispatchers.IO) {
            try {
                val movieDtoDeferred = async { moviesApi.getMovieById(movieId) }
                val movieCastDeferred = async { moviesApi.getMovieCastById(movieId) }

                val movie = MovieDetailsMapper.map(
                    movieDetails = movieDtoDeferred.await(),
                    actors = movieCastDeferred.await().actors,
                    imageConfig = getImageConfigs(),
                    isFavorite = favoriteMovieIdList.contains(movieId),
                )

                LoadResult.Success(movie)
            } catch (e: Exception) {
                LoadResult.Error(e.message.orEmpty())
            }
        }

    private suspend fun getImageConfigs(): ImageConfig {
        if (cachedImageConfig == null) {
            val imageConfigDto = moviesApi.getConfiguration().imageConfig
            cachedImageConfig = ImageConfigMapper.map(imageConfigDto)
        }

        return requireNotNull(cachedImageConfig)
    }

    fun addMovieToFavorites(movie: Movie): Movie {
        val updatedMovie = movie.copy(isFavorite = !movie.isFavorite)
        if (updatedMovie.isFavorite) {
            favoriteMovieIdList.add(movie.id)
        } else {
            favoriteMovieIdList.remove(movie.id)
        }

        return updatedMovie
    }

    companion object {
        private val favoriteMovieIdList = mutableSetOf<Int>()
        private val moviesDataSource: MoviesDataSource = MoviesDataSource(RetrofitModule.moviesApi)

        fun getInstance() = moviesDataSource
    }

}