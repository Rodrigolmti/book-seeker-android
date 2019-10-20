package com.ciandt.book.seeker.domain

sealed class Result<out T : Any> {
    class Success<T : Any>(val data: T) : Result<T>()
    class Error(val failure: Failure) : Result<Nothing>()
}