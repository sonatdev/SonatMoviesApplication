package com.sonat.movies.view.common

import androidx.lifecycle.Observer

/**
 * An [Observer] for [Event]s, simplifying the pattern of checking if the [Event]'s
 * content has  already been handled in case of [ViewState.Error], so [onEvent] is
 * *only* called if the [Event]'s error contents has not been handled.
 * Does nothing for other [ViewState] types.
 */
class ViewStateEventObserver<T>(
    private val onEvent: (ViewState<T>) -> Unit
) : Observer<Event<ViewState<T>>> {

    override fun onChanged(event: Event<ViewState<T>>?) {
        when (event?.getContent()) {
            is ViewState.Success -> onEvent(event.getContent())
            is ViewState.Loading -> onEvent(event.getContent())
            is ViewState.Error -> {
                event.getContentIfNotHandled()?.let {
                    onEvent(it)
                }
            }
        }
    }
}