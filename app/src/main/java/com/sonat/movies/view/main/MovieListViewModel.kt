package com.sonat.movies.view.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonat.movies.data.models.Movie
import com.sonat.movies.domain.MoviesDataSource
import com.sonat.movies.domain.MoviesResult
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val moviesDataSource: MoviesDataSource
) : ViewModel() {

    val movieListLoadingState: LiveData<ViewState>
        get() = _mutableMovieListLiveData

    private val _mutableMovieListLiveData = MutableLiveData<ViewState>()

    fun getMovies(context: Context) =
        viewModelScope.launch {
            _mutableMovieListLiveData.value = ViewState.Loading

            val newViewState = when (val movieListResult = moviesDataSource.getMovies(context)) {
                is MoviesResult.MoviesList.Success -> ViewState.Success(movieListResult.data)
                is MoviesResult.MoviesList.Error -> ViewState.Error(movieListResult.errorMessage)
            }

            _mutableMovieListLiveData.value = newViewState
        }

    sealed class ViewState {

        object Loading : ViewState()

        class Success(val data: List<Movie>) : ViewState()

        class Error(val errorMessage: String) : ViewState()
    }
}