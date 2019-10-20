package com.ciandt.book.seeker.domain.use_case

import com.ciandt.book.seeker.domain.Failure
import com.ciandt.book.seeker.domain.Result
import com.ciandt.book.seeker.domain.model.Book
import com.ciandt.book.seeker.domain.repository.Repository

class GetBookUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(): Result<Book> {
        repository.getBook()?.let { book ->
            return Result.Success(book)
        } ?: return Result.Error(Failure.UnknownError)
    }
}