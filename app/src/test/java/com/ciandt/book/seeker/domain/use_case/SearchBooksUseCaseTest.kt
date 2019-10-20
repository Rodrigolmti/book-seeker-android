package com.ciandt.book.seeker.domain.use_case

import com.ciandt.book.seeker.TestDataFactory
import com.ciandt.book.seeker.domain.Failure
import com.ciandt.book.seeker.domain.Result
import com.ciandt.book.seeker.domain.model.Book
import com.ciandt.book.seeker.domain.repository.Repository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
internal class SearchBooksUseCaseTest {

    private val repository = mockk<Repository>()
    private val getQueryUseCase = mockk<GetQueryUseCase>()

    private val useCae = SearchBooksUseCase(repository, getQueryUseCase)

    @Test
    fun `on invoke should return list of books`() = runBlockingTest {
        val query = "android"
        prepareScenario(query)

        val result = useCae()

        if (result is Result.Success) {
            assertTrue(result.data.isNotEmpty())
        }
    }

    @Test
    fun `on invoke should call searchBooksByName`() = runBlockingTest {
        val query = "asimov"
        prepareScenario(query)

        useCae()

        coVerify(exactly = 1) { repository.searchBooksByName(query) }
    }

    @Test
    fun `on invoke with no query should return error`() = runBlockingTest {
        coEvery { getQueryUseCase() } returns null

        val result = useCae()

        if (result is Result.Error) {
            assertTrue(result.failure is Failure.UnknownError)
        }
    }

    private fun prepareScenario(query: String) {
        coEvery { getQueryUseCase() } returns query
        coEvery {
            repository.searchBooksByName(query)
        } returns Result.Success(
            listOf(
                TestDataFactory.makeBook(),
                TestDataFactory.makeBook()
            )
        )
    }
}