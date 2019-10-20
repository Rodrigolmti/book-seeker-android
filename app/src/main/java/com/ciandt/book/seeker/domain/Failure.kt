package com.ciandt.book.seeker.domain

sealed class Failure {
    object ServiceError : Failure()
    object StorageError : Failure()
    object UnknownError : Failure()
    abstract class FeatureFailure : Failure()
}