package com.sonat.movies.view.listeners

fun interface RecyclerItemClickListener<T> {
    fun onItemClick(item: T)
}