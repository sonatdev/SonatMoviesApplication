package com.sonat.movies.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sonat.movies.R
import com.sonat.movies.data.models.Actor
import com.sonat.movies.view.listeners.RecyclerItemClickListener

class ActorsRecyclerAdapter(
    private val actorClickListener: RecyclerItemClickListener<Actor>
) : RecyclerView.Adapter<ActorsViewHolder>() {

    private var actors: List<Actor> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ActorsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_actor, parent, false),
            actorClickListener,
        )

    override fun onBindViewHolder(holder: ActorsViewHolder, position: Int) {
        holder.onBind(actors[position])
    }

    override fun getItemCount() = actors.size

    fun bindActors(actors: List<Actor>) {
        this.actors = actors
        notifyDataSetChanged()
    }
}