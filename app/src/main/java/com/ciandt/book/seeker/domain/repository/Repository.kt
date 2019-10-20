package com.ciandt.book.seeker.domain.repository

import com.ciandt.book.seeker.domain.Result
import com.ciandt.book.seeker.domain.model.Book
import com.ciandt.book.seeker.domain.model.Historic

interface Repository {

    suspend fun searchBooksByName(query: String) : Result<List<Book>>

    suspend fun getListOfHistoric(): Result<List<Historic>>

    suspend fun saveSearchedQuery(query: String)

    suspend fun updateQuery(query: String)

    suspend fun getQuery(): String?

    suspend fun clearHistoric()

    suspend fun updateBook(book: Book)

    suspend fun getBook(): Book?

    suspend fun clearBook()

    suspend fun clearQuery()
}
