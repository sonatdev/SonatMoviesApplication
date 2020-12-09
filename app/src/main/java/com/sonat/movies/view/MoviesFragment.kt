package com.sonat.movies.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.sonat.movies.R
import com.sonat.movies.data.models.Movie
import com.sonat.movies.domain.MoviesDataSource
import com.sonat.movies.view.adapters.MoviesRecyclerAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesFragment : Fragment() {

    private lateinit var moviesDataSource: MoviesDataSource
    private lateinit var moviesRecycler: RecyclerView
    private var movieSelectionListener: MovieSelectionListener? = null

    private val retrieveMoviesCoroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context !is MovieSelectionListener) {
            throw IllegalArgumentException(
                "Context type ${context.javaClass.simpleName} should implement movie selection listener"
            )
        }

        movieSelectionListener = context
        moviesDataSource = MoviesDataSource(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_movies, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviesRecycler = view.findViewById(R.id.recycler_movies)
        moviesRecycler.adapter = MoviesRecyclerAdapter {
            movieSelectionListener?.onMovieSelected(it.id)
        }

        retrieveMoviesCoroutineScope.launch {
            val movies = moviesDataSource.getMovies()

            withContext(Dispatchers.Main) { bindMoviesData(movies) }
        }
    }

    private fun bindMoviesData(movies: List<Movie>) =
        with(moviesRecycler.adapter as MoviesRecyclerAdapter) {
            bindMovies(movies)
            notifyDataSetChanged()
        }

    override fun onDetach() {
        super.onDetach()
        movieSelectionListener = null
    }

    interface MovieSelectionListener {
        fun onMovieSelected(movieId: Int)
    }
}