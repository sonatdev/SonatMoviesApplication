package com.sonat.movies.view.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sonat.movies.R
import com.sonat.movies.data.models.Actor
import com.sonat.movies.view.listeners.RecyclerItemClickListener

class ActorsViewHolder(
    itemView: View,
    private val actorClickListener: RecyclerItemClickListener<Actor>
) : RecyclerView.ViewHolder(itemView) {

    private val nameText = itemView.findViewById<TextView>(R.id.text_actor_name)
    private val photoImage = itemView.findViewById<ImageView>(R.id.image_actor_photo)

    private lateinit var actor: Actor

    init {
        itemView.setOnClickListener { actorClickListener.onItemClick(actor) }
    }

    fun onBind(actor: Actor) {
        this.actor = actor
        nameText.text = actor.name
        photoImage.setImageResource(actor.photoResId)
    }
}