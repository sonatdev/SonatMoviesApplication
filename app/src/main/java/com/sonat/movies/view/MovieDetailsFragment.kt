package com.sonat.movies.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sonat.movies.R
import com.sonat.movies.data.models.Movie
import com.sonat.movies.domain.MoviesDataSource
import com.sonat.movies.view.adapters.ActorsRecyclerAdapter
import com.sonat.movies.view.glide.CustomBackgroundTarget
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailsFragment : Fragment() {

    private lateinit var moviesDataSource: MoviesDataSource

    private var movieId: Int = 0
    private lateinit var backTextView: TextView
    private lateinit var posterImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var tagsTextView: TextView
    private lateinit var pgRatingTextView: TextView
    private lateinit var reviewsTextView: TextView
    private lateinit var storylineTextView: TextView
    private lateinit var actorsRecyclerView: RecyclerView

    private val retrieveMovieCoroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        moviesDataSource = MoviesDataSource(requireContext())

        arguments?.let {
            movieId = it.getInt(MOVIE_ID_PARAM)
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
        setOnClickListeners()
        getMovieAndUpdateViewState()
    }

    private fun findViews(view: View) {
        with(view) {
            backTextView = findViewById(R.id.text_back_btn)
            posterImageView = findViewById(R.id.image_movie_poster)
            titleTextView = findViewById(R.id.text_movie_name)
            tagsTextView = findViewById(R.id.text_movie_tags)
            pgRatingTextView = findViewById(R.id.text_movie_pg_rating)
            reviewsTextView = findViewById(R.id.text_movie_reviews)
            storylineTextView = findViewById(R.id.text_movie_storyline_content)
            actorsRecyclerView = findViewById(R.id.recycler_actors)
        }
    }

    private fun setOnClickListeners() {
        backTextView.setOnClickListener { activity?.onBackPressed() }
        actorsRecyclerView.adapter = ActorsRecyclerAdapter {
            Toast.makeText(
                context,
                "Actor's profile screen is not implemented yet",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun getMovieAndUpdateViewState() {
        retrieveMovieCoroutineScope.launch {
            val movie = moviesDataSource.getMovieById(movieId)
            withContext(Dispatchers.Main) { bindMovieData(movie) }
        }
    }

    private fun bindMovieData(movie: Movie) {
        val context = requireContext()
        with(movie) {
            titleTextView.text = title
            tagsTextView.text = genres.joinToString { it.name }
            pgRatingTextView.text =
                context.getString(R.string.movie_details_label_pg_rating, pgRating)
            reviewsTextView.text =
                context.getString(R.string.movie_details_label_reviews, reviewsCount)
            storylineTextView.text = storyline

            Glide.with(context)
                .load(backdropUrl)
                .fitCenter()
                .placeholder(ColorDrawable(Color.BLACK))
                .into(CustomBackgroundTarget(posterImageView))

            with(actorsRecyclerView.adapter as ActorsRecyclerAdapter) {
                bindActors(actors)
                notifyDataSetChanged()
            }
        }
    }

    companion object {
        private const val MOVIE_ID_PARAM = "movieId"

        fun newInstance(movieId: Int) =
            MovieDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(MOVIE_ID_PARAM, movieId)
                }
            }
    }
}