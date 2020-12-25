package com.sonat.movies.view.details

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonat.movies.data.models.Movie
import com.sonat.movies.domain.MoviesDataSource
import com.sonat.movies.domain.MoviesResult
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val moviesDataSource: MoviesDataSource
) : ViewModel() {

    val movieLoadingState: LiveData<ViewState>
        get() = _mutableMovieLoadingState

    private val _mutableMovieLoadingState = MutableLiveData<ViewState>()

    fun getMovieById(movieId: Int, context: Context) =
        viewModelScope.launch {
            _mutableMovieLoadingState.value = ViewState.Loading

            val newViewState =
                when (val movieResult = moviesDataSource.getMovieById(movieId, context)) {
                    is MoviesResult.MovieDetails.Success -> ViewState.Success(movieResult.data)
                    is MoviesResult.MovieDetails.Error -> ViewState.Error(movieResult.errorMessage)
                }

            _mutableMovieLoadingState.value = newViewState
        }

    sealed class ViewState {

        object Loading : ViewState()

        class Success(val data: Movie) : ViewState()

        class Error(val errorMessage: String) : ViewState()
    }
}