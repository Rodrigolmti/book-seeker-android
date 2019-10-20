package com.ciandt.book.seeker.util

class LiveDataSingleEvent<out T>(private val content: T) {

    private var hasBeenHandled = false

    val contentIfNotHandled: T?
        get() = if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
}