package com.ciandt.book.seeker.ui.book_detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ciandt.book.seeker.TestDataFactory
import com.ciandt.book.seeker.domain.Failure
import com.ciandt.book.seeker.domain.Result
import com.ciandt.book.seeker.domain.use_case.ClearBookUseCase
import com.ciandt.book.seeker.domain.use_case.GetBookUseCase
import com.ciandt.book.seeker.util.CoroutinesTestRule
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
internal class BookDetailViewModelTest {

    @Suppress("unused")
    @get:Rule
    var instantTask = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val getBookUseCase = mockk<GetBookUseCase>()
    private val clearBookUseCase = mockk<ClearBookUseCase>()

    private lateinit var viewModel: BookDetailViewModel

    @Before
    fun setup() {
        viewModel = BookDetailViewModel(getBookUseCase, clearBookUseCase)
    }

    @Test
    fun `on start should get book`() = runBlockingTest {
        val book = TestDataFactory.makeBook()
        val action = BookDetailViewModelAction.BookRetrieved(book)
        coEvery { getBookUseCase() } returns Result.Success(book)

        viewModel.onStart()

        coVerify(exactly = 1) { getBookUseCase() }
        assertEquals(action, viewModel.actionLiveData.value)
    }

    @Test
    fun `on start should send error action`() = runBlockingTest {
        val action = BookDetailViewModelAction.FailToRetrieveBook
        coEvery { getBookUseCase() } returns Result.Error(Failure.UnknownError)

        viewModel.onStart()
        assertEquals(action, viewModel.actionLiveData.value)
    }

    @Test
    fun `on destroy should clear book`() = runBlockingTest {
        coEvery { clearBookUseCase() } just runs

        viewModel.onDestroy()

        coVerify(exactly = 1) { clearBookUseCase() }
    }
}