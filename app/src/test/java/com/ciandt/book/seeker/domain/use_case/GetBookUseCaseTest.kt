package com.ciandt.book.seeker.domain.use_case

import com.ciandt.book.seeker.domain.Failure
import com.ciandt.book.seeker.domain.Result
import com.ciandt.book.seeker.domain.model.Book
import com.ciandt.book.seeker.domain.repository.Repository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
internal class GetBookUseCaseTest {

    private val repository = mockk<Repository>()

    private val useCase = GetBookUseCase(repository)

    @Test
    fun `on invoke should call clearBook`() = runBlockingTest {
        prepareScenario()

        useCase()

        coVerify(exactly = 1) { repository.getBook() }
    }

    @Test
    fun `on invoke should return success`() = runBlockingTest {
        prepareScenario()

        val result = useCase()

        if (result is Result.Success) {
            assertNotNull(result.data)
        }
    }

    @Test
    fun `on invoke with invalid book should return error`() = runBlockingTest {
        coEvery { repository.getBook() } returns null

        val result = useCase()

        if (result is Result.Error) {
            assertTrue(result.failure is Failure.UnknownError)
        }
    }

    private fun prepareScenario() {
        coEvery { repository.getBook() } returns Book(
            name = "A senha que não esra senha",
            authorName = "Alehandro farijo",
            description = "Desccubra o porque a senha era uma senha",
            coverImage = "senha.png"
        )
    }
}