package com.sonat.movies.view.glide

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

class CustomBackgroundTarget(private val imageView: ImageView) : CustomTarget<Drawable>() {

    override fun onLoadCleared(placeholder: Drawable?) {
        // nothing to do
    }

    override fun onResourceReady(
        resource: Drawable,
        transition: Transition<in Drawable>?
    ) {
        imageView.background = resource
    }
}