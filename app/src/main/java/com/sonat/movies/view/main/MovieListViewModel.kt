package com.sonat.movies.view.main

import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonat.movies.data.models.Movie
import com.sonat.movies.domain.LoadResult
import com.sonat.movies.domain.MoviesDataSource
import com.sonat.movies.view.common.ViewState
import com.sonat.movies.view.util.ImageUtils
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val moviesDataSource: MoviesDataSource
) : ViewModel() {

    val movieListLoadingState: LiveData<ViewState<List<Movie>>>
        get() = _mutableMovieListLiveData

    private val _mutableMovieListLiveData = MutableLiveData<ViewState<List<Movie>>>()

    init {
        getMovies()
    }

    private fun getMovies() =
        viewModelScope.launch {
            _mutableMovieListLiveData.value = ViewState.Loading()

            val newViewState = when (val movieListResult = moviesDataSource.getMovies()) {
                is LoadResult.Success -> ViewState.Success(movieListResult.data)
                is LoadResult.Error -> ViewState.Error(movieListResult.errorMessage)
            }

            _mutableMovieListLiveData.value = newViewState
        }

    fun onFavoriteIconClick(likeIcon: ImageView, movie: Movie) =
        viewModelScope.launch {
            moviesDataSource.addMovieToFavorites(movie)
            ImageUtils.setLikeIconColor(likeIcon, movie.isFavorite)
        }
}