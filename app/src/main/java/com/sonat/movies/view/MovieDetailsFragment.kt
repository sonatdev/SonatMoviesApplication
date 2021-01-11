package com.sonat.movies.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sonat.movies.R
import com.sonat.movies.data.models.Movie
import com.sonat.movies.view.adapters.ActorsRecyclerAdapter
import com.sonat.movies.view.common.ViewState
import com.sonat.movies.view.details.MovieDetailsViewModel
import com.sonat.movies.view.details.MovieDetailsViewModelFactory
import com.sonat.movies.view.glide.CustomBackgroundTarget
import com.sonat.movies.view.util.ImageUtils.setLikeIconColor

class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private val movieViewModel: MovieDetailsViewModel by viewModels {
        MovieDetailsViewModelFactory(
            arguments?.getInt(MOVIE_ID_PARAM)!!,
            requireContext().applicationContext
        )
    }

    private lateinit var backTextView: TextView
    private lateinit var posterImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var tagsTextView: TextView
    private lateinit var pgRatingTextView: TextView
    private lateinit var reviewsTextView: TextView
    private lateinit var storylineTextView: TextView
    private lateinit var ratingBar: RatingBar
    private lateinit var isFavoriteImage: ImageView
    private lateinit var castLabelTextView: TextView
    private lateinit var storylineLabelTextView: TextView
    private lateinit var actorsRecyclerView: RecyclerView
    private lateinit var progressBar: ContentLoadingProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findViews(view)
        setOnClickListeners()

        movieViewModel.movieLoadingState.observe(viewLifecycleOwner, this::setViewState)
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
            ratingBar = findViewById(R.id.rating_bar_movie)
            isFavoriteImage = findViewById(R.id.image_movie_like)
            castLabelTextView = findViewById(R.id.text_label_movie_cast)
            storylineLabelTextView = findViewById(R.id.text_label_movie_storyline)
            actorsRecyclerView = findViewById(R.id.recycler_actors)
            progressBar = findViewById(R.id.progress_bar)
        }
    }

    private fun setOnClickListeners() {
        backTextView.setOnClickListener { activity?.onBackPressed() }
        isFavoriteImage.setOnClickListener {
            movieViewModel.onFavoriteIconClick(it as ImageView)
        }

        actorsRecyclerView.adapter = ActorsRecyclerAdapter {
            Toast.makeText(
                context,
                "Actor's profile screen is not implemented yet",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setViewState(state: ViewState<Movie>) =
        when (state) {
            is ViewState.Loading -> showLoading(isLoading = true)

            is ViewState.Success -> {
                showLoading(isLoading = false)
                showMovieViews()
                bindMovieData(state.data)
            }

            is ViewState.Error -> {
                showLoading(isLoading = false)
                showError(state.errorMessage)
            }
        }

    private fun showMovieViews() {
        ratingBar.isVisible = true
        isFavoriteImage.isVisible = true
        storylineTextView.isVisible = true
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
            ratingBar.rating = reviewsRating / 2

            Glide.with(context)
                .load(backdropUrl)
                .fitCenter()
                .placeholder(ColorDrawable(Color.BLACK))
                .into(CustomBackgroundTarget(posterImageView))

            setLikeIconColor(isFavoriteImage, isFavorite)

            castLabelTextView.isVisible = actors.isNotEmpty()
            with(actorsRecyclerView.adapter as ActorsRecyclerAdapter) {
                bindActors(actors)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) progressBar.show() else progressBar.hide()
    }

    private fun showError(errorMessage: String) =
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()

    companion object {
        private const val MOVIE_ID_PARAM = "movieId"

        fun newInstance(movieId: Int) =
            MovieDetailsFragment().apply {
                arguments = bundleOf(MOVIE_ID_PARAM to movieId)
            }
    }
}