package com.sonat.movies.view.common

sealed class ViewState<T> {

    class Success<T>(val data: T) : ViewState<T>()

    class Loading<T> : ViewState<T>()

    class Error<T>(val errorMessage: String) : ViewState<T>()
}