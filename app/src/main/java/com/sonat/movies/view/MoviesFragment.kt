package com.sonat.movies.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.sonat.movies.R
import com.sonat.movies.data.models.Movie
import com.sonat.movies.view.adapters.MoviesRecyclerAdapter
import com.sonat.movies.view.common.ItemWithPosition
import com.sonat.movies.view.common.ViewState
import com.sonat.movies.view.common.ViewStateEventObserver
import com.sonat.movies.view.listeners.RecyclerItemWithLikeIconClickListener
import com.sonat.movies.view.main.MovieListViewModel
import com.sonat.movies.view.main.MovieListViewModelFactory

class MoviesFragment : Fragment(R.layout.fragment_movies) {

    private lateinit var moviesRecycler: RecyclerView
    private lateinit var progressBar: ContentLoadingProgressBar

    private var movieSelectionListener: MovieSelectionListener? = null
    private val movieListViewModel: MovieListViewModel by viewModels {
        MovieListViewModelFactory(requireContext().applicationContext)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context !is MovieSelectionListener) {
            throw IllegalArgumentException(
                "Context type ${context.javaClass.simpleName} should implement movie selection listener"
            )
        }

        movieSelectionListener = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findViews(view)

        movieListViewModel.movieListLoadingState
            .observe(viewLifecycleOwner, ViewStateEventObserver(this::setViewState))
        movieListViewModel.movieFavoriteState
            .observe(viewLifecycleOwner, ViewStateEventObserver(this::updateItemViewState))
    }

    private fun findViews(view: View) {
        with(view) {
            progressBar = findViewById(R.id.progress_bar)
            moviesRecycler = findViewById(R.id.recycler_movies)
        }

        moviesRecycler.adapter =
            MoviesRecyclerAdapter(object : RecyclerItemWithLikeIconClickListener<Movie> {
                override fun onLikeIconClick(item: Movie, position: Int) {
                    movieListViewModel.onFavoriteIconClick(item, position)
                }

                override fun onItemClick(item: Movie) {
                    movieSelectionListener?.onMovieSelected(item.id)
                }
            })
    }

    private fun setViewState(state: ViewState<List<Movie>>) {
        when (state) {
            is ViewState.Loading -> showLoading(true)

            is ViewState.Success -> {
                showLoading(false)
                bindMoviesData(state.data)
            }

            is ViewState.Error -> {
                showLoading(isLoading = false)
                showError(state.errorMessage)
            }
        }
    }

    private fun updateItemViewState(state: ViewState<ItemWithPosition<Movie>>) {
        when (state) {
            is ViewState.Loading -> showLoading(true)

            is ViewState.Success -> {
                showLoading(false)
                updateMovieItem(state.data.item, state.data.position)
            }

            is ViewState.Error -> {
                showLoading(isLoading = false)
                showError(state.errorMessage)
            }
        }
    }

    private fun bindMoviesData(movies: List<Movie>) =
        with(moviesRecycler.adapter as MoviesRecyclerAdapter) {
            bindMovies(movies)
        }

    private fun updateMovieItem(movie: Movie, position: Int) =
        with(moviesRecycler.adapter as MoviesRecyclerAdapter) {
            updateMovieItem(movie, position)
        }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) progressBar.show() else progressBar.hide()
    }

    private fun showError(errorMessage: String) =
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()

    override fun onDetach() {
        super.onDetach()
        movieSelectionListener = null
    }

    interface MovieSelectionListener {
        fun onMovieSelected(movieId: Int)
    }
}