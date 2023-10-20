package com.otarbakh.rickyandmorty.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<T> : ViewModel() {
    protected val _state = MutableStateFlow<T?>(null)
    val state: StateFlow<T?> = _state

    protected fun updateState(newState: T?) {
        _state.value = newState
    }

    protected fun launchDataLoad(block: suspend () -> T) {
        viewModelScope.launch {
            try {
                val result = block()
                updateState(result)
            } catch (e: Exception) {

                updateState(null)
            }
        }
    }
}
