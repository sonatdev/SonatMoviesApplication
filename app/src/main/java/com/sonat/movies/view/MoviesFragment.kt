package com.sonat.movies.view

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import com.sonat.movies.R

class MoviesFragment : Fragment() {

    private var isMovieLiked: Boolean = false //ToDo: temporal solution
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

        view.findViewById<View>(R.id.card_movie)
            .setOnClickListener { movieSelectionListener?.onMovieSelected(MOVIE_ID_PARAM) }

        view.findViewById<ImageView>(R.id.image_movie_like)
            .setOnClickListener { changeLikeIconColor(it as ImageView) }
    }

    override fun onDetach() {
        super.onDetach()
        movieSelectionListener = null
    }

    private fun changeLikeIconColor(likeIcon: ImageView) {
        val newColorId = if (isMovieLiked) R.color.white else R.color.radical_red
        val color = ContextCompat.getColor(context!!, newColorId)
        ImageViewCompat.setImageTintList(likeIcon, ColorStateList.valueOf(color))
        isMovieLiked = !isMovieLiked
    }

    companion object {
        const val MOVIE_ID_PARAM = "movie_id"
    }

    interface MovieSelectionListener {
        fun onMovieSelected(movieId: String)
    }
}