package com.ciandt.book.seeker.domain.use_case

import com.ciandt.book.seeker.domain.repository.Repository
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
internal class ClearHistoricUseCaseTest {

    private val repository = mockk<Repository>()

    private val useCase = ClearHistoricUseCase(repository)

    @Test
    fun `invoke should call clearHistoric`() = runBlockingTest {
        coEvery { repository.clearHistoric() } just runs

        useCase()

        coVerify(exactly = 1) { repository.clearHistoric() }
    }
}
