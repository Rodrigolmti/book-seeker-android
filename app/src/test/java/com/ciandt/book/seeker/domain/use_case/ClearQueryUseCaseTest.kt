package com.ciandt.book.seeker.domain.use_case

import com.ciandt.book.seeker.domain.repository.Repository
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
internal class ClearQueryUseCaseTest {

    private val repository = mockk<Repository>()

    private val useCase = ClearQueryUseCase(repository)

    @Test
    fun `on invoke should clear query`() = runBlockingTest {
        coEvery { repository.clearQuery() } just runs

        useCase()

        coVerify(exactly = 1) { repository.clearQuery() }
    }
}