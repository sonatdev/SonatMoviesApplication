package com.sonat.movies.view.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sonat.movies.domain.MoviesDataSource

class MovieListViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when (modelClass) {
            MovieListViewModel::class.java -> MovieListViewModel(MoviesDataSource(context))
            else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
        } as T
}