package com.sonat.movies.view.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonat.movies.data.models.Movie
import com.sonat.movies.domain.LoadResult
import com.sonat.movies.domain.MoviesDataSource
import com.sonat.movies.view.common.Event
import com.sonat.movies.view.common.ViewState
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    movieId: Int,
    private val moviesDataSource: MoviesDataSource
) : ViewModel() {

    val movieLoadingState: LiveData<Event<ViewState<Movie>>>
        get() = _mutableMovieLoadingState

    private lateinit var movie: Movie
    private val _mutableMovieLoadingState = MutableLiveData<Event<ViewState<Movie>>>()

    init {
        getMovieById(movieId)
    }

    private fun getMovieById(movieId: Int) =
        viewModelScope.launch {
            _mutableMovieLoadingState.value = Event(ViewState.Loading())

            val newViewState = when (val movieResult = moviesDataSource.getMovieById(movieId)) {
                is LoadResult.Success -> {
                    movie = movieResult.data
                    ViewState.Success(movie)
                }

                is LoadResult.Error -> ViewState.Error(movieResult.errorMessage)
            }

            _mutableMovieLoadingState.value = Event(newViewState)
        }

    fun onFavoriteIconClick() =
        viewModelScope.launch {
            movie = moviesDataSource.addMovieToFavorites(movie)
            _mutableMovieLoadingState.value = Event(ViewState.Success(movie))
        }
}