package com.sonat.movies.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.sonat.movies.R
import com.sonat.movies.domain.MoviesDataSource
import com.sonat.movies.view.adapters.MoviesRecyclerAdapter

class MoviesFragment : Fragment() {

    private val moviesDataSource = MoviesDataSource()
    private lateinit var moviesRecycler: RecyclerView
    private var movieSelectionListener: MovieSelectionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context !is MovieSelectionListener) {
            throw IllegalArgumentException(
                "Context type ${context.javaClass.simpleName} should implement movie selection listener")
        }

        movieSelectionListener = context
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
            movieSelectionListener?.onMovieSelected(it.title)
        }

        updateDate()
    }

    private fun updateDate() {
        (moviesRecycler.adapter as MoviesRecyclerAdapter)
            .bindMovies(moviesDataSource.getMovies())
    }

    override fun onDetach() {
        super.onDetach()
        movieSelectionListener = null
    }

    interface MovieSelectionListener {
        fun onMovieSelected(movieId: String)
    }
}