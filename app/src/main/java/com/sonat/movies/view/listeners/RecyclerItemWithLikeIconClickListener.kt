package com.sonat.movies.view.listeners

interface RecyclerItemWithLikeIconClickListener<T> : RecyclerItemClickListener<T> {
    fun onLikeIconClick(item: T, position: Int)
}