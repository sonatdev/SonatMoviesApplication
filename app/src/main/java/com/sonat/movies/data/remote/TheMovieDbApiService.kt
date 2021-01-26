package com.sonat.movies.data.remote

import com.sonat.movies.data.remote.models.*
import retrofit2.http.GET
import retrofit2.http.Path

interface TheMovieDbApiService {

    @GET("configuration")
    suspend fun getConfiguration(): ConfigResponse

    @GET("movie/popular")
    suspend fun getPopularMovies(): MovieListResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieById(@Path("movie_id") movieId: Int): MovieDetailsDto

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCastById(@Path("movie_id") movieId: Int): CastResponse

    @GET("genre/movie/list")
    suspend fun getAllGenres(): GenreListResponse

}