package com.sonat.movies.view.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sonat.movies.domain.MoviesDataSource
import com.sonat.movies.view.details.MovieDetailsViewModel
import com.sonat.movies.view.main.MovieListViewModel

class ViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when (modelClass) {
            MovieListViewModel::class.java -> MovieListViewModel(MoviesDataSource.getInstance())
            MovieDetailsViewModel::class.java -> MovieDetailsViewModel(MoviesDataSource.getInstance())
            else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
        } as T
}