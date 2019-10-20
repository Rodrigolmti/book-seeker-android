package com.ciandt.book.seeker.ui.search_result

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import com.ciandt.book.seeker.domain.Result
import com.ciandt.book.seeker.domain.model.Book
import com.ciandt.book.seeker.domain.use_case.ClearQueryUseCase
import com.ciandt.book.seeker.domain.use_case.SearchBooksUseCase
import com.ciandt.book.seeker.domain.use_case.UpdateBookUseCase
import com.ciandt.book.seeker.util.BaseViewModel
import com.ciandt.book.seeker.util.LiveDataSingleEvent

sealed class SearchResultViewModelAction {
    data class BookList(val list: List<Book>) : SearchResultViewModelAction()
    object BookUpdated : SearchResultViewModelAction()
    object GetBookListFail : SearchResultViewModelAction()
    object QueryCleared : SearchResultViewModelAction()
}

class SearchResultViewModel(
    private val searchBooksUseCase: SearchBooksUseCase,
    private val updateBookUseCase: UpdateBookUseCase,
    private val clearQueryUseCase: ClearQueryUseCase
) : BaseViewModel(), LifecycleObserver {

    private val _actionMutableLiveData =
        MutableLiveData<LiveDataSingleEvent<SearchResultViewModelAction>>()

    val actionLiveData
        get() = _actionMutableLiveData

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        launchData {
            when (val response = searchBooksUseCase()) {
                is Result.Success -> {
                    _actionMutableLiveData.value =
                        LiveDataSingleEvent(SearchResultViewModelAction.BookList(response.data))
                }
                is Result.Error -> {
                    _actionMutableLiveData.value =
                        LiveDataSingleEvent(SearchResultViewModelAction.GetBookListFail)
                }
            }
        }
    }

    fun clearQuery() = launchData {
        clearQueryUseCase()
        _actionMutableLiveData.value = LiveDataSingleEvent(SearchResultViewModelAction.QueryCleared)
    }

    fun updateBook(book: Book) = launchData {
        updateBookUseCase(book)
        _actionMutableLiveData.value = LiveDataSingleEvent(SearchResultViewModelAction.BookUpdated)
    }
}