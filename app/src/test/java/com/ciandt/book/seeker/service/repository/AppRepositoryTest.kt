package com.ciandt.book.seeker.service.repository

import com.ciandt.book.seeker.TestDataFactory
import com.ciandt.book.seeker.domain.Result
import com.ciandt.book.seeker.domain.datasource.LocalDataSource
import com.ciandt.book.seeker.domain.datasource.RemoteDataSource
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
internal class AppRepositoryTest {

    private val localDataSource = mockk<LocalDataSource>()
    private val remoteDataSource = mockk<RemoteDataSource>()

    private val repository = AppRepository(localDataSource, remoteDataSource)

    @Test
    fun `call get historic list should return a list of historic`() = runBlockingTest {
        val historic = listOf(
            TestDataFactory.makeHistoric(),
            TestDataFactory.makeHistoric()
        )
        coEvery { localDataSource.getListOfHistoric() } returns Result.Success(historic)

        val result = repository.getListOfHistoric()

        assertTrue(result is Result.Success)
        assertEquals(result.data, historic)
        coVerify(exactly = 1) { localDataSource.getListOfHistoric() }
    }

    @Test
    fun `call update query should save a new query`() = runBlockingTest {
        val query = "android"
        coEvery { localDataSource.updateQuery(query) } just runs

        repository.updateQuery(query)

        coVerify(exactly = 1) { localDataSource.updateQuery(query) }
    }

    @Test
    fun `call get query should return one query`() = runBlockingTest {
        val query = "android"
        coEvery { localDataSource.getQuery() } returns query

        val result = repository.getQuery()

        coVerify(exactly = 1) { localDataSource.getQuery() }
        assertEquals(query, result)
    }

    @Test
    fun `call search book by name should return list of book`() = runBlockingTest {
        val query = "android"
        val books = listOf(
            TestDataFactory.makeBook(),
            TestDataFactory.makeBook()
        )
        coEvery { remoteDataSource.searchBooksByName(query) } returns Result.Success(books)

        val result = repository.searchBooksByName(query)

        assertTrue(result is Result.Success)
        assertEquals(result.data, books)
        coVerify(exactly = 1) { remoteDataSource.searchBooksByName(query) }
    }

    @Test
    fun `call save searched query should save historic`() = runBlockingTest {
        val query = "android"
        coEvery { localDataSource.saveSearchedQuery(query) } just runs

        repository.saveSearchedQuery(query)

        coVerify(exactly = 1) { localDataSource.saveSearchedQuery(query) }
    }

    @Test
    fun `call clear historic should clear all historic`() = runBlockingTest {
        coEvery { localDataSource.clearHistoric() } just runs

        repository.clearHistoric()

        coVerify(exactly = 1) { localDataSource.clearHistoric() }
    }

    @Test
    fun `call clear query should clear saved query`() = runBlockingTest {
        coEvery { localDataSource.clearQuery() } just runs

        repository.clearQuery()

        coVerify(exactly = 1) { localDataSource.clearQuery() }
    }

    @Test
    fun `call update book should save a new book`() = runBlockingTest {
        val book = TestDataFactory.makeBook()
        coEvery { localDataSource.updateBook(book) } just runs

        repository.updateBook(book)

        coVerify(exactly = 1) { localDataSource.updateBook(book) }
    }

    @Test
    fun `call get book should return one book`() = runBlockingTest {
        val book = TestDataFactory.makeBook()
        coEvery { localDataSource.getBook() } returns book

        val result = repository.getBook()

        coVerify(exactly = 1) { localDataSource.getBook() }
        assertEquals(book, result)
    }

    @Test
    fun `call clear book should clear saved book`() = runBlockingTest {
        coEvery { localDataSource.clearBook() } just runs

        repository.clearBook()

        coVerify(exactly = 1) { localDataSource.clearBook() }
    }
}