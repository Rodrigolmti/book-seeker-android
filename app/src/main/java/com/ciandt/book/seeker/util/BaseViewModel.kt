package com.ciandt.book.seeker.util

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

typealias Block = suspend () -> Unit

abstract class BaseViewModel : ViewModel() {

    private val mViewModelJob = Job()
    private val mUiScope = CoroutineScope(Dispatchers.Main + mViewModelJob)

    override fun onCleared() {
        super.onCleared()
        mViewModelJob.cancel()
    }

    protected fun launchData(block: Block): Job {
        return mUiScope.launch {
            block()
        }
    }
}