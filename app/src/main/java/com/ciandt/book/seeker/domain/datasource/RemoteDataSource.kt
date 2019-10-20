package com.ciandt.book.seeker.domain.datasource

import com.ciandt.book.seeker.domain.Failure
import com.ciandt.book.seeker.domain.Result
import com.ciandt.book.seeker.domain.model.Book
import com.ciandt.book.seeker.service.mappers.mapBookResponseToBook
import com.ciandt.book.seeker.service.remote.BookWebService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface RemoteDataSource {

    suspend fun searchBooksByName(query: String): Result<List<Book>>
}

class AppRemoteDataSource(
    private val bookWebService: BookWebService
) : RemoteDataSource {

    override suspend fun searchBooksByName(query: String): Result<List<Book>> =
        withContext(Dispatchers.IO) {
            try {
                bookWebService.searchBooksByName(query).await().let { response ->
                    Result.Success(response.results.map { bookResponse ->
                        bookResponse.mapBookResponseToBook()
                    })
                }
            } catch (error: Exception) {
                Result.Error(Failure.ServiceError)
            }
        }
}