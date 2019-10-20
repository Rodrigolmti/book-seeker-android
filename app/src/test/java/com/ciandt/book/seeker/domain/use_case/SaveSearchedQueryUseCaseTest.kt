package com.ciandt.book.seeker.domain.use_case

import com.ciandt.book.seeker.domain.repository.Repository
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
internal class SaveSearchedQueryUseCaseTest {

    private val repository = mockk<Repository>()
    private val getQueryUseCase = mockk<GetQueryUseCase>()

    private val useCase = SaveSearchedQueryUseCase(repository, getQueryUseCase)

    @Test
    fun `on invoke should save searched query`() = runBlockingTest {
        val query = "android"
        coEvery { getQueryUseCase() } returns query
        coEvery { repository.saveSearchedQuery(query) } just runs
        coEvery { repository.clearQuery() } just runs

        useCase()

        coVerify(exactly = 1) { repository.saveSearchedQuery(query) }
    }
}