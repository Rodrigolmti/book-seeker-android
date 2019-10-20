package com.ciandt.book.seeker.service.repository

import com.ciandt.book.seeker.domain.Result
import com.ciandt.book.seeker.domain.datasource.LocalDataSource
import com.ciandt.book.seeker.domain.datasource.RemoteDataSource
import com.ciandt.book.seeker.domain.model.Book
import com.ciandt.book.seeker.domain.model.Historic
import com.ciandt.book.seeker.domain.repository.Repository

class AppRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : Repository {

    override suspend fun getListOfHistoric(): Result<List<Historic>> =
        localDataSource.getListOfHistoric()

    override suspend fun updateQuery(query: String) =
        localDataSource.updateQuery(query)

    override suspend fun getQuery(): String? =
        localDataSource.getQuery()

    override suspend fun searchBooksByName(query: String): Result<List<Book>> =
        remoteDataSource.searchBooksByName(query)

    override suspend fun saveSearchedQuery(query: String) =
        localDataSource.saveSearchedQuery(query)

    override suspend fun clearHistoric() =
        localDataSource.clearHistoric()

    override suspend fun clearQuery() =
        localDataSource.clearQuery()

    override suspend fun updateBook(book: Book) =
        localDataSource.updateBook(book)

    override suspend fun getBook(): Book? =
        localDataSource.getBook()

    override suspend fun clearBook() =
        localDataSource.clearBook()

}

