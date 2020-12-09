package com.sonat.movies.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.sonat.movies.R
import com.sonat.movies.data.models.Movie
import com.sonat.movies.domain.MoviesDataSource
import com.sonat.movies.view.adapters.ActorsRecyclerAdapter

class MovieDetailsFragment : Fragment() {

    private val moviesDataSource = MoviesDataSource()

    private lateinit var movie: Movie
    private lateinit var backTextView: TextView
    private lateinit var titleTextView: TextView
    private lateinit var tagsTextView: TextView
    private lateinit var pgRatingTextView: TextView
    private lateinit var reviewsTextView: TextView
    private lateinit var storylineTextView: TextView
    private lateinit var actorsRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val movieTitle = it.getString(MOVIE_ID_PARAM)
            movie = moviesDataSource.getMovieByTitle(movieTitle!!)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_movie_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findViews(view)
        initViewsState(movie)

        backTextView.setOnClickListener { activity?.onBackPressed() }
        actorsRecyclerView.adapter = ActorsRecyclerAdapter {
            Toast.makeText(
                context,
                "Actor's profile screen is not implemented yet",
                Toast.LENGTH_SHORT
            ).show()
        }.apply {
            bindActors(movie.actors)
        }
    }

    private fun findViews(view: View) {
        with(view) {
            backTextView = findViewById(R.id.text_back_btn)
            titleTextView = findViewById(R.id.text_movie_name)
            tagsTextView = findViewById(R.id.text_movie_tags)
            pgRatingTextView = findViewById(R.id.text_movie_pg_rating)
            reviewsTextView = findViewById(R.id.text_movie_reviews)
            storylineTextView = findViewById(R.id.text_movie_storyline_content)
            actorsRecyclerView = findViewById(R.id.recycler_actors)
        }
    }

    private fun initViewsState(movie: Movie) {
        val context = requireContext()
        with(movie) {
            titleTextView.text = title
            tagsTextView.text = genres.joinToString { it.name }
            pgRatingTextView.text =
                context.getString(R.string.movie_details_label_pg_rating, pgRating)
            reviewsTextView.text =
                context.getString(R.string.movie_details_label_reviews, reviewsCount)
            storylineTextView.text = storyline
        }
    }

    companion object {
        private const val MOVIE_ID_PARAM = "movieId"

        fun newInstance(movieId: String) =
            MovieDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(MOVIE_ID_PARAM, movieId)
                }
            }
    }
}