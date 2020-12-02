package com.sonat.movies.view.adapters

import android.content.res.ColorStateList
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.sonat.movies.R
import com.sonat.movies.data.models.Movie

class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val nameText = itemView.findViewById<TextView>(R.id.text_movie_name)
    private val tagsText = itemView.findViewById<TextView>(R.id.text_movie_tags)
    private val pgRatingText = itemView.findViewById<TextView>(R.id.text_movie_pg_rating)
    private val reviewsCountText = itemView.findViewById<TextView>(R.id.text_movie_reviews)
    private val durationInMinText = itemView.findViewById<TextView>(R.id.text_movie_duration)
    private val posterImage = itemView.findViewById<ImageView>(R.id.image_movie_poster)
    private val isFavoriteImage = itemView.findViewById<ImageView>(R.id.image_movie_like)

    fun onBind (movie: Movie) {
        nameText.text = movie.title
        tagsText.text = movie.tags.joinToString()
        pgRatingText.text = itemView.context.getString(R.string.movies_label_movie_pg_rating, movie.pgRating)
        reviewsCountText.text = itemView.context.getString(R.string.movies_label_movie_reviews, movie.reviewsCount)
        durationInMinText.text =
            itemView.context.getString(R.string.movies_label_movie_duration, movie.durationInMin)
        posterImage.background = ContextCompat.getDrawable(itemView.context, movie.posterResId)

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