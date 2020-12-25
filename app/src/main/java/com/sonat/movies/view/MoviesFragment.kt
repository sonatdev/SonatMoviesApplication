package com.sonat.movies.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.sonat.movies.R
import com.sonat.movies.data.models.Movie
import com.sonat.movies.domain.MoviesDataSource
import com.sonat.movies.view.adapters.MoviesRecyclerAdapter
import com.sonat.movies.view.common.ViewModelFactory
import com.sonat.movies.view.listeners.RecyclerItemWithLikeIconClickListener
import com.sonat.movies.view.main.MovieListViewModel

class MoviesFragment : Fragment(R.layout.fragment_movies) {

    private lateinit var moviesRecycler: RecyclerView
    private var movieSelectionListener: MovieSelectionListener? = null
    private val movieListViewModel: MovieListViewModel by viewModels { ViewModelFactory() }

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

        moviesRecycler = view.findViewById(R.id.recycler_movies)
        moviesRecycler.adapter =
            MoviesRecyclerAdapter(object : RecyclerItemWithLikeIconClickListener<Movie> {
                override fun onLikeIconClick(item: Movie) =
                    MoviesDataSource.addMovieToFavorites(item)

                override fun onItemClick(item: Movie) {
                    movieSelectionListener?.onMovieSelected(item.id)
                }
            })

        movieListViewModel.movieListLoadingState.observe(viewLifecycleOwner, this::setViewState)
        movieListViewModel.getMovies(requireContext())
    }

    private fun setViewState(state: MovieListViewModel.ViewState) {
        when (state) {
            MovieListViewModel.ViewState.Loading -> showLoading(true)

            is MovieListViewModel.ViewState.Success -> {
                showLoading(isLoading = false)
                bindMoviesData(state.data)
            }

            is MovieListViewModel.ViewState.Error -> {
                showLoading(isLoading = false)
                showError(state.errorMessage)
            }
        }
    }

    private fun bindMoviesData(movies: List<Movie>) =
        with(moviesRecycler.adapter as MoviesRecyclerAdapter) {
            bindMovies(movies)
        }

    private fun showLoading(isLoading: Boolean) {
        Toast.makeText(requireContext(), "LOADING: $isLoading...", Toast.LENGTH_SHORT).show()
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