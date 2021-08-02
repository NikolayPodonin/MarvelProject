package com.podonin.base_ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freeletics.flowredux.dsl.FlowReduxStateMachine
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 *  Base [ViewModel] for state machine handling.
 *  You should put some [ReduxAction] to [dispatch] method and then wait for a new [ReduxState] in [state] field.
 */
@ExperimentalCoroutinesApi
open class ReduxViewModel<S: ReduxState, A: ReduxAction>(
    private val stateMachine: FlowReduxStateMachine<S, A>
): ViewModel() {

    val state: LiveData<S>
    get() = _state

    private val _state = MutableLiveData<S>()

    init {
        stateMachine.state
            .onEach { _state.value = it }
            .launchIn(viewModelScope)
    }

    fun dispatch(action: A) {
        viewModelScope.launch {
            stateMachine.dispatch(action)
        }
    }
}