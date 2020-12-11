package com.sonat.movies.view.adapters

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sonat.movies.R
import com.sonat.movies.data.models.Movie
import com.sonat.movies.view.glide.CustomBackgroundTarget
import com.sonat.movies.view.listeners.RecyclerItemClickListener

class MoviesViewHolder(
    itemView: View,
    private val movieClickListener: RecyclerItemClickListener<Movie>,
) : RecyclerView.ViewHolder(itemView) {

    private lateinit var movie: Movie

    private val nameText = itemView.findViewById<TextView>(R.id.text_movie_name)
    private val tagsText = itemView.findViewById<TextView>(R.id.text_movie_tags)
    private val pgRatingText = itemView.findViewById<TextView>(R.id.text_movie_pg_rating)
    private val reviewsCountText = itemView.findViewById<TextView>(R.id.text_movie_reviews)
    private val durationInMinText = itemView.findViewById<TextView>(R.id.text_movie_duration)
    private val posterImage = itemView.findViewById<ImageView>(R.id.image_movie_poster)
    private val ratingBar = itemView.findViewById<RatingBar>(R.id.rating_bar_movie)
    private val isFavoriteImage = itemView.findViewById<ImageView>(R.id.image_movie_like)

    init {
        itemView.setOnClickListener { movieClickListener.onItemClick(movie) }
    }

    fun onBind(movie: Movie) {
        this.movie = movie

        nameText.text = movie.title
        tagsText.text = movie.genres.joinToString { it.name }
        pgRatingText.text =
            itemView.context.getString(R.string.movies_label_movie_pg_rating, movie.pgRating)
        reviewsCountText.text =
            itemView.context.getString(R.string.movies_label_movie_reviews, movie.reviewsCount)
        durationInMinText.text =
            itemView.context.getString(R.string.movies_label_movie_duration, movie.durationInMin)
        ratingBar.rating = movie.reviewsRating / 2

        Glide.with(itemView.context)
            .load(movie.posterUrl)
            .centerCrop()
            .placeholder(ColorDrawable(Color.BLACK))
            .into(CustomBackgroundTarget(posterImage))

        setLikeIconColor(isFavoriteImage, movie)
        isFavoriteImage.setOnClickListener { makeMovieFavorite(it as ImageView, movie) }
    }

    private fun makeMovieFavorite(likeIcon: ImageView, movie: Movie) {
        movie.isFavorite = !movie.isFavorite
        setLikeIconColor(likeIcon, movie)
    }

    private fun setLikeIconColor(likeIcon: ImageView, movie: Movie) {
        val colorId = if (movie.isFavorite) R.color.radical_red else R.color.white
        val color = ContextCompat.getColor(itemView.context, colorId)
        ImageViewCompat.setImageTintList(likeIcon, ColorStateList.valueOf(color))
    }

}