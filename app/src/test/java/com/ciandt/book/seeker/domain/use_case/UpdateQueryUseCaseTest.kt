package com.ciandt.book.seeker.domain.use_case

import com.ciandt.book.seeker.domain.Result
import com.ciandt.book.seeker.domain.repository.Repository
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
internal class UpdateQueryUseCaseTest {

    private val repository = mockk<Repository>()

    private val useCae = UpdateQueryUseCase(repository)

    @Test
    fun `on invoke with valid query should return success`() = runBlockingTest {
        val query = "android"
        prepareScenario(query)

        val result = useCae(query)

        assertTrue(result is Result.Success)
    }

    @Test
    fun `on invoke with valid query should call updateQuery`() = runBlockingTest {
        val query = "android"
        prepareScenario(query)

        useCae(query)

        coVerify(exactly = 1) { repository.updateQuery(query) }
    }

    @Test
    fun `on invoke with empty query should return invalid query`() = runBlockingTest {
        val query = ""
        prepareScenario(query)

        val result = useCae(query)

        if (result is Result.Error) {
            val failure = result.failure as UpdateQueryUseCase.UpdateQueryFailure
            assertTrue(failure.errors.isNotEmpty())
            assertTrue(failure.errors.contains(UpdateQueryUseCase.UpdateQueryFailure.ErrorType.INVALID_QUERY))
        }
    }

    @Test
    fun `on invoke with short query should return invalid query length`() = runBlockingTest {
        val query = "as"
        prepareScenario(query)

        val result = useCae(query)

        if (result is Result.Error) {
            val failure = result.failure as UpdateQueryUseCase.UpdateQueryFailure
            assertTrue(failure.errors.isNotEmpty())
            assertTrue(failure.errors.contains(UpdateQueryUseCase.UpdateQueryFailure.ErrorType.INVALID_QUERY_LENGTH))
        }
    }

    private fun prepareScenario(query: String) {
        coEvery { repository.updateQuery(query) } just runs
    }
}