package com.sonat.movies.view.details

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sonat.movies.domain.MoviesDataSource

class MovieDetailsViewModelFactory(
    private val movieId: Int,
    private val context: Context,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when (modelClass) {
            MovieDetailsViewModel::class.java -> MovieDetailsViewModel(
                movieId,
                MoviesDataSource(context)
            )
            else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
        } as T
}