package com.sonat.movies.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonat.movies.data.models.Movie
import com.sonat.movies.domain.LoadResult
import com.sonat.movies.domain.MoviesDataSource
import com.sonat.movies.view.common.Event
import com.sonat.movies.view.common.ItemWithPosition
import com.sonat.movies.view.common.ViewState
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val moviesDataSource: MoviesDataSource
) : ViewModel() {

    val movieListLoadingState: LiveData<Event<ViewState<List<Movie>>>>
        get() = _mutableMovieListLiveData
    val movieFavoriteState: LiveData<Event<ViewState<ItemWithPosition<Movie>>>>
        get() = _mutableMovieFavoriteState

    private val _mutableMovieListLiveData = MutableLiveData<Event<ViewState<List<Movie>>>>()
    private val _mutableMovieFavoriteState =
        MutableLiveData<Event<ViewState<ItemWithPosition<Movie>>>>()

    init {
        getMovies()
    }

    private fun getMovies() =
        viewModelScope.launch {
            _mutableMovieListLiveData.value = Event(ViewState.Loading())

            val newViewState = when (val movieListResult = moviesDataSource.getMovies()) {
                is LoadResult.Success -> ViewState.Success(movieListResult.data)
                is LoadResult.Error -> ViewState.Error(movieListResult.errorMessage)
            }

            _mutableMovieListLiveData.value = Event(newViewState)
        }

    fun onFavoriteIconClick(movie: Movie, position: Int) =
        viewModelScope.launch {
            val updatedMovie = moviesDataSource.addMovieToFavorites(movie)
            _mutableMovieFavoriteState.value =
                Event(ViewState.Success(ItemWithPosition(updatedMovie, position)))
        }
}