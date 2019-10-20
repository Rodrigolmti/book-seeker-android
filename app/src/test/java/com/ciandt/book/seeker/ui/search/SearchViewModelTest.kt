package com.ciandt.book.seeker.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ciandt.book.seeker.domain.Failure
import com.ciandt.book.seeker.domain.Result
import com.ciandt.book.seeker.domain.model.Historic
import com.ciandt.book.seeker.domain.use_case.ClearHistoricUseCase
import com.ciandt.book.seeker.domain.use_case.GetHistoricUseCase
import com.ciandt.book.seeker.domain.use_case.SaveSearchedQueryUseCase
import com.ciandt.book.seeker.domain.use_case.UpdateQueryUseCase
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
internal class SearchViewModelTest {

    @Suppress("unused")
    @get:Rule
    var instantTask = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val updateQueryUseCase = mockk<UpdateQueryUseCase>()
    private val getHistoricUseCase = mockk<GetHistoricUseCase>()
    private val clearHistoricUseCase = mockk<ClearHistoricUseCase>()
    private val saveSearchedQueryUseCase = mockk<SaveSearchedQueryUseCase>()

    private lateinit var viewModel: SearchViewModel

    @Before
    fun setup() {
        viewModel = SearchViewModel(
            updateQueryUseCase,
            getHistoricUseCase,
            clearHistoricUseCase,
            saveSearchedQueryUseCase
        )
    }

    @Test
    fun `on start should get historic`() = runBlockingTest {
        val historic = listOf(Historic(query = "android"), Historic(query = "asimov"))
        val singleEvent = LiveDataSingleEvent(SearchViewModelAction.HistoricList(historic))
        coEvery { getHistoricUseCase() } returns Result.Success(historic)

        viewModel.onStart()

        singleEvent.contentIfNotHandled?.let { expected ->
            viewModel.actionLiveData.value?.contentIfNotHandled?.let { actual ->
                assertEquals(expected, actual)
            }
        }
    }

    @Test
    fun `on start should return error`() = runBlockingTest {
        val singleEvent = LiveDataSingleEvent(SearchViewModelAction.GetHistoricFail)
        coEvery { getHistoricUseCase() } returns Result.Error(Failure.StorageError)

        viewModel.onStart()

        singleEvent.contentIfNotHandled?.let { expected ->
            viewModel.actionLiveData.value?.contentIfNotHandled?.let { actual ->
                assertEquals(expected, actual)
            }
        }
    }

    @Test
    fun `call clear historic should run clearHistoricUseCase`() = runBlockingTest {
        coEvery { clearHistoricUseCase() } just runs

        viewModel.clearHistoric()

        coVerify(exactly = 1) { clearHistoricUseCase() }
    }

    @Test
    fun `call clear historic should send historic cleared action`() = runBlockingTest {
        val singleEvent = LiveDataSingleEvent(SearchViewModelAction.HistoricCleared)
        coEvery { clearHistoricUseCase() } just runs

        viewModel.clearHistoric()

        singleEvent.contentIfNotHandled?.let { expected ->
            viewModel.actionLiveData.value?.contentIfNotHandled?.let { actual ->
                assertEquals(expected, actual)
            }
        }
    }

    @Test
    fun `call update query should send query updated action`() = runBlockingTest {
        val query = "android"
        val singleEvent = LiveDataSingleEvent(SearchViewModelAction.QueryUpdated)
        coEvery { updateQueryUseCase.invoke(query) } returns Result.Success(Unit)
        coEvery { saveSearchedQueryUseCase() } just runs

        viewModel.updateQuery(query)

        singleEvent.contentIfNotHandled?.let { expected ->
            viewModel.actionLiveData.value?.contentIfNotHandled?.let { actual ->
                assertEquals(expected, actual)
            }
        }
    }

    @Test
    fun `call update query should save searched query`() = runBlockingTest {
        val query = "android"
        coEvery { updateQueryUseCase.invoke(query) } returns Result.Success(Unit)
        coEvery { saveSearchedQueryUseCase() } just runs

        viewModel.updateQuery(query)

        coVerify(exactly = 1) { saveSearchedQueryUseCase() }
    }

    @Test
    fun `call update query should return invalid query action`() = runBlockingTest {
        val query = ""
        val singleEvent = LiveDataSingleEvent(SearchViewModelAction.InvalidQuery)
        val errors = listOf(UpdateQueryUseCase.UpdateQueryFailure.ErrorType.INVALID_QUERY)
        coEvery { updateQueryUseCase.invoke(query) } returns Result.Error(
            UpdateQueryUseCase.UpdateQueryFailure(
                errors
            )
        )

        viewModel.updateQuery(query)

        singleEvent.contentIfNotHandled?.let { expected ->
            viewModel.actionLiveData.value?.contentIfNotHandled?.let { actual ->
                assertEquals(expected, actual)
            }
        }
    }

    @Test
    fun `call update query should return query too small action`() = runBlockingTest {
        val query = ""
        val singleEvent = LiveDataSingleEvent(SearchViewModelAction.QueryToSmall)
        val errors = listOf(UpdateQueryUseCase.UpdateQueryFailure.ErrorType.INVALID_QUERY_LENGTH)
        coEvery { updateQueryUseCase.invoke(query) } returns Result.Error(
            UpdateQueryUseCase.UpdateQueryFailure(
                errors
            )
        )

        viewModel.updateQuery(query)

        singleEvent.contentIfNotHandled?.let { expected ->
            viewModel.actionLiveData.value?.contentIfNotHandled?.let { actual ->
                assertEquals(expected, actual)
            }
        }
    }
}