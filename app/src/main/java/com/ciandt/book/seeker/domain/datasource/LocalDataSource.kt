package com.ciandt.book.seeker.domain.datasource

import com.ciandt.book.seeker.domain.Failure
import com.ciandt.book.seeker.domain.Result
import com.ciandt.book.seeker.domain.model.Book
import com.ciandt.book.seeker.domain.model.Historic
import com.ciandt.book.seeker.storage.database.AppDatabase
import com.ciandt.book.seeker.storage.mappers.mapHistoricStorageToHistoric
import com.ciandt.book.seeker.storage.model.HistoricStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface LocalDataSource {

    suspend fun getListOfHistoric(): Result<List<Historic>>

    suspend fun saveSearchedQuery(query: String)

    suspend fun updateQuery(query: String)

    suspend fun getQuery(): String?

    suspend fun updateBook(book: Book)

    suspend fun getBook(): Book?

    suspend fun clearBook()

    suspend fun clearHistoric()

    suspend fun clearQuery()
}

class AppLocalDataSource(
    private val appDatabase: AppDatabase
) : LocalDataSource {

    private var query: String? = null
    private var book: Book? = null

    override suspend fun getListOfHistoric(): Result<List<Historic>> = withContext(Dispatchers.IO) {
        try {
            Result.Success(appDatabase.database().bookDao().findAll().map { historic ->
                historic.mapHistoricStorageToHistoric()
            })
        } catch (error: Exception) {
            Result.Error(Failure.StorageError)
        }
    }

    override suspend fun clearHistoric() = withContext(Dispatchers.IO) {
        try {
            appDatabase.database().bookDao().deleteAll()
        } catch (error: Exception) {
            // If error do nothing
        }
    }

    override suspend fun saveSearchedQuery(query: String) = withContext(Dispatchers.IO) {
        try {
            val searched = HistoricStorage(query = query)
            appDatabase.database().bookDao().insert(searched)
        } catch (error: Exception) {
            // If error do nothing
        }
    }

    override suspend fun updateBook(book: Book) {
        this.book = book
    }

    override suspend fun getBook(): Book? {
        return book
    }

    override suspend fun clearBook() {
        book = null
    }

    override suspend fun updateQuery(query: String) {
        this.query = query
    }

    override suspend fun getQuery() = query

    override suspend fun clearQuery() {
        query = null
    }
}