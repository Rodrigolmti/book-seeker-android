package com.ciandt.book.seeker.domain.use_case

import com.ciandt.book.seeker.TestDataFactory
import com.ciandt.book.seeker.domain.repository.Repository
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
internal class UpdateBookUseCaseTest {

    private val repository = mockk<Repository>()

    private val useCase = UpdateBookUseCase(repository)

    @Test
    fun `on invoke should call clearBook`() = runBlockingTest {
        val book = TestDataFactory.makeBook()
        coEvery { repository.updateBook(book) } just runs

        useCase(book)

        coVerify(exactly = 1) { repository.updateBook(book) }
    }
}