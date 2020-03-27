package com.cotel.architecture.base.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel

abstract class BaseViewModel<VS, SE> : ViewModel() {

    protected val _viewState = MutableLiveData<VS>()
    val viewState: LiveData<VS>
        get() = _viewState

    protected val _sideEffects = Channel<SE>()
    val sideEffects: ReceiveChannel<SE>
        get() = _sideEffects
}
