package com.ciandt.book.seeker.ui.search

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import com.ciandt.book.seeker.domain.Result
import com.ciandt.book.seeker.domain.model.Historic
import com.ciandt.book.seeker.domain.use_case.ClearHistoricUseCase
import com.ciandt.book.seeker.domain.use_case.GetHistoricUseCase
import com.ciandt.book.seeker.domain.use_case.SaveSearchedQueryUseCase
import com.ciandt.book.seeker.domain.use_case.UpdateQueryUseCase
import com.ciandt.book.seeker.util.BaseViewModel
import com.ciandt.book.seeker.util.LiveDataSingleEvent

sealed class SearchViewModelAction {
    data class HistoricList(val list: List<Historic>) : SearchViewModelAction()
    object GetHistoricFail : SearchViewModelAction()
    object QueryUpdated : SearchViewModelAction()
    object HistoricCleared : SearchViewModelAction()
    object QueryToSmall : SearchViewModelAction()
    object InvalidQuery : SearchViewModelAction()
}

class SearchViewModel(
    private val updateQueryUseCase: UpdateQueryUseCase,
    private val getHistoricUseCase: GetHistoricUseCase,
    private val clearHistoricUseCase: ClearHistoricUseCase,
    private val saveSearchedQueryUseCase: SaveSearchedQueryUseCase
) : BaseViewModel(), LifecycleObserver {

    private val _actionMutableLiveData =
        MutableLiveData<LiveDataSingleEvent<SearchViewModelAction>>()

    val actionLiveData
        get() = _actionMutableLiveData

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() = launchData {
        when (val response = getHistoricUseCase()) {
            is Result.Success -> {
                _actionMutableLiveData.value =
                    LiveDataSingleEvent(SearchViewModelAction.HistoricList(response.data))
            }
            is Result.Error -> {
                _actionMutableLiveData.value =
                    LiveDataSingleEvent(SearchViewModelAction.GetHistoricFail)
            }
        }
    }

    fun clearHistoric() = launchData {
        clearHistoricUseCase()
        _actionMutableLiveData.value =
            LiveDataSingleEvent(SearchViewModelAction.HistoricCleared)
    }

    fun updateQuery(query: String) = launchData {
        when (val response = updateQueryUseCase.invoke(query)) {
            is Result.Success -> {
                saveSearchedQueryUseCase()
                _actionMutableLiveData.value =
                    LiveDataSingleEvent(SearchViewModelAction.QueryUpdated)
            }
            is Result.Error -> {
                (response.failure as UpdateQueryUseCase.UpdateQueryFailure).also { failure ->
                    if (failure.errors.contains(
                            UpdateQueryUseCase.UpdateQueryFailure.ErrorType.INVALID_QUERY
                        )
                    ) {
                        _actionMutableLiveData.value =
                            LiveDataSingleEvent(SearchViewModelAction.InvalidQuery)
                    }
                    if (failure.errors.contains(
                            UpdateQueryUseCase.UpdateQueryFailure.ErrorType.INVALID_QUERY_LENGTH
                        )
                    ) {
                        _actionMutableLiveData.value =
                            LiveDataSingleEvent(SearchViewModelAction.QueryToSmall)
                    }
                }
            }
        }
    }
}