package com.ciandt.book.seeker.domain.use_case

import com.ciandt.book.seeker.TestDataFactory
import com.ciandt.book.seeker.domain.Result
import com.ciandt.book.seeker.domain.repository.Repository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
internal class GetHistoricUseCaseTest {

    private val repository = mockk<Repository>()

    private val useCase = GetHistoricUseCase(repository)

    @Test
    fun `on invoke should call getListOfHistoric`() = runBlockingTest {
        prepareScenario()

        useCase()

        coVerify(exactly = 1) { repository.getListOfHistoric() }
    }

    @Test
    fun `on invoke should return historic list`() = runBlockingTest {
        prepareScenario()

        val result = useCase()

        if (result is Result.Success) {
            assertTrue(result.data.isNotEmpty())
        }
    }

    private fun prepareScenario() {
        coEvery {
            repository.getListOfHistoric()
        } returns Result.Success(
            listOf(
                TestDataFactory.makeHistoric(),
                TestDataFactory.makeHistoric()
            )
        )
    }
}