package com.ciandt.book.seeker.ui.search_result

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ciandt.book.seeker.TestDataFactory
import com.ciandt.book.seeker.domain.Failure
import com.ciandt.book.seeker.domain.Result
import com.ciandt.book.seeker.domain.use_case.ClearQueryUseCase
import com.ciandt.book.seeker.domain.use_case.SearchBooksUseCase
import com.ciandt.book.seeker.domain.use_case.UpdateBookUseCase
import com.ciandt.book.seeker.util.CoroutinesTestRule
import com.ciandt.book.seeker.util.LiveDataSingleEvent
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
internal class SearchResultViewModelTest {

    @Suppress("unused")
    @get:Rule
    var instantTask = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val searchBooksUseCase = mockk<SearchBooksUseCase>()
    private val updateBookUseCase = mockk<UpdateBookUseCase>()
    private val clearQueryUseCase = mockk<ClearQueryUseCase>()

    private lateinit var viewModel: SearchResultViewModel

    @Before
    fun setup() {
        viewModel = SearchResultViewModel(searchBooksUseCase, updateBookUseCase, clearQueryUseCase)
    }

    @Test
    fun `on start should search book`() = runBlockingTest {
        val books = listOf(
            TestDataFactory.makeBook(),
            TestDataFactory.makeBook()
        )
        val singleEvent = LiveDataSingleEvent(SearchResultViewModelAction.BookList(books))
        coEvery { searchBooksUseCase() } returns Result.Success(books)

        viewModel.onStart()

        singleEvent.contentIfNotHandled?.let { expected ->
            viewModel.actionLiveData.value?.contentIfNotHandled?.let { actual ->
                assertEquals(expected, actual)
            }
        }
    }

    @Test
    fun `on start should return fail action`() = runBlockingTest {
        val singleEvent = LiveDataSingleEvent(SearchResultViewModelAction.GetBookListFail)
        coEvery { searchBooksUseCase() } returns Result.Error(Failure.UnknownError)

        viewModel.onStart()

        singleEvent.contentIfNotHandled?.let { expected ->
            viewModel.actionLiveData.value?.contentIfNotHandled?.let { actual ->
                assertEquals(expected, actual)
            }
        }
    }

    @Test
    fun `call clear query should remove query`() = runBlockingTest {
        val singleEvent = LiveDataSingleEvent(SearchResultViewModelAction.QueryCleared)
        coEvery { clearQueryUseCase() } just runs

        viewModel.clearQuery()

        coVerify(exactly = 1) { clearQueryUseCase() }
        singleEvent.contentIfNotHandled?.let { expected ->
            viewModel.actionLiveData.value?.contentIfNotHandled?.let { actual ->
                assertEquals(expected, actual)
            }
        }
    }

    @Test
    fun `call update book should save book`() = runBlockingTest {
        val singleEvent = LiveDataSingleEvent(SearchResultViewModelAction.BookUpdated)
        val book = TestDataFactory.makeBook()
        coEvery { updateBookUseCase(book) } just runs

        viewModel.updateBook(book)

        coVerify(exactly = 1) { updateBookUseCase(book) }
        singleEvent.contentIfNotHandled?.let { expected ->
            viewModel.actionLiveData.value?.contentIfNotHandled?.let { actual ->
                assertEquals(expected, actual)
            }
        }
    }
}