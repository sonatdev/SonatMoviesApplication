package com.sonat.movies.view.common

class Event<out T>(private val content: T) {

    private var hasBeenHandled = false

    fun getContent(): T = content

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}
