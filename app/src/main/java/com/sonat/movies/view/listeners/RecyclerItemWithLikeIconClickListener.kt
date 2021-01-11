package com.sonat.movies.view.listeners

import android.widget.ImageView

interface RecyclerItemWithLikeIconClickListener<T> : RecyclerItemClickListener<T> {
    fun onLikeIconClick(likeIcon: ImageView, item: T)
}