package com.ciandt.book.seeker.ui.book_detail

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import com.ciandt.book.seeker.domain.Result
import com.ciandt.book.seeker.domain.model.Book
import com.ciandt.book.seeker.domain.use_case.ClearBookUseCase
import com.ciandt.book.seeker.domain.use_case.GetBookUseCase
import com.ciandt.book.seeker.util.BaseViewModel

sealed class BookDetailViewModelAction {
    data class BookRetrieved(val book: Book) : BookDetailViewModelAction()
    object FailToRetrieveBook : BookDetailViewModelAction()
}

class BookDetailViewModel(
    private val getBookUseCase: GetBookUseCase,
    private val clearBookUseCase: ClearBookUseCase
) : BaseViewModel(), LifecycleObserver {

    private val _actionMutableLiveData = MutableLiveData<BookDetailViewModelAction>()

    val actionLiveData
        get() = _actionMutableLiveData

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() = launchData {
        when (val response = getBookUseCase()) {
            is Result.Success -> {
                _actionMutableLiveData.value =
                    BookDetailViewModelAction.BookRetrieved(response.data)
            }
            is Result.Error -> {
                _actionMutableLiveData.value = BookDetailViewModelAction.FailToRetrieveBook
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() = launchData {
        clearBookUseCase()
    }
}