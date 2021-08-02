package com.podonin.base_ui

import com.freeletics.flowredux.dsl.FlowReduxStateMachine
import kotlinx.coroutines.ExperimentalCoroutinesApi


/**
 * Base class for redux state machine.
 * You should call [spec] in the init block for handle [ReduxState] with [inState] method.
 */
@ExperimentalCoroutinesApi
open class ReduxStateMachine<S: ReduxState, A: ReduxAction>(
    initialState: S
): FlowReduxStateMachine<S, A>(initialState)