package com.sonat.movies.domain

sealed class LoadResult<T> {

    class Success<T>(val data: T) : LoadResult<T>()

    class Error<T>(val errorMessage: String) : LoadResult<T>()

}