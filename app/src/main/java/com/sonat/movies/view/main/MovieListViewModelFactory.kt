package com.sonat.movies.view.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sonat.movies.domain.MoviesDataSource

class MovieListViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when (modelClass) {
            MovieListViewModel::class.java -> MovieListViewModel(
                MoviesDataSource.getInstance()
            )
            else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
        } as T
}