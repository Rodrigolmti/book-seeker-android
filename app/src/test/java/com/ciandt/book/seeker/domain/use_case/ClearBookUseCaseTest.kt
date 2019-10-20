package com.ciandt.book.seeker.domain.use_case

import com.ciandt.book.seeker.domain.repository.Repository
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
internal class ClearBookUseCaseTest {

    private val repository = mockk<Repository>()

    private val useCase = ClearBookUseCase(repository)

    @Test
    fun `on invoke should call clearBook`() = runBlockingTest {
        coEvery { repository.clearBook() } just runs

        useCase()

        coVerify(exactly = 1) { repository.clearBook() }
    }
}