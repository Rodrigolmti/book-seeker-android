package com.ciandt.book.seeker.domain.use_case

import com.ciandt.book.seeker.domain.repository.Repository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
internal class GetQueryUseCaseTest {

    private val repository = mockk<Repository>()

    private val useCase = GetQueryUseCase(repository)

    @Test
    fun `on invoke should call getQuery`() = runBlockingTest {
        coEvery { repository.getQuery() } returns "Isaac Asimov"

        useCase()

        coVerify(exactly = 1) { repository.getQuery() }
    }

    @Test
    fun `on invoke should return one query`() = runBlockingTest {
        val query = "Isaac Asimov"
        coEvery { repository.getQuery() } returns query

        val result = useCase()

        assertEquals(query, result)
    }
}