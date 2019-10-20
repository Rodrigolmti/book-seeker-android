package com.ciandt.book.seeker.domain.use_case

import com.ciandt.book.seeker.domain.model.Book
import com.ciandt.book.seeker.domain.repository.Repository

class UpdateBookUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(book: Book) = repository.updateBook(book)
}