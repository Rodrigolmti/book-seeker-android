package com.ciandt.book.seeker.domain.use_case

import com.ciandt.book.seeker.domain.Failure
import com.ciandt.book.seeker.domain.Result
import com.ciandt.book.seeker.domain.model.Book
import com.ciandt.book.seeker.domain.repository.Repository

class SearchBooksUseCase(
    private val repository: Repository,
    private val getQueryUseCase: GetQueryUseCase
) {

    suspend operator fun invoke(): Result<List<Book>> {
        return getQueryUseCase()?.let { query ->
            repository.searchBooksByName(query)
        } ?: Result.Error(Failure.UnknownError)
    }
}