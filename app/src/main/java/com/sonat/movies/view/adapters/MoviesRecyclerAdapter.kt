package com.sonat.movies.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sonat.movies.R
import com.sonat.movies.data.models.Movie
import com.sonat.movies.view.listeners.RecyclerItemClickListener

class MoviesRecyclerAdapter(
    private val movieClickListener: RecyclerItemClickListener<Movie>
) : RecyclerView.Adapter<MoviesViewHolder>() {

    private var movies: List<Movie> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MoviesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false),
            movieClickListener
        )

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.onBind(movies[position])
    }

    override fun getItemCount() = movies.size

    fun bindMovies(movies: List<Movie>) {
        this.movies = movies
    }
}