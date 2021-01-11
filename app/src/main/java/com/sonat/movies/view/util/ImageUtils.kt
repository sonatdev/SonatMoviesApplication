package com.sonat.movies.view.util

import android.content.res.ColorStateList
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import com.sonat.movies.R

object ImageUtils {

    fun setLikeIconColor(likeIcon: ImageView, isFavorite: Boolean) {
        val colorId = if (isFavorite) R.color.radical_red else R.color.white
        val color = ContextCompat.getColor(likeIcon.context, colorId)
        ImageViewCompat.setImageTintList(likeIcon, ColorStateList.valueOf(color))
    }
}
