package com.podonin.marvel_details.ui

import com.podonin.base_ui.ReduxViewModel
import com.podonin.marvel_details.redux.DetailsAction
import com.podonin.marvel_details.redux.DetailsState
import com.podonin.marvel_details.redux.DetailsStateMachine
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class DetailsViewModel(
    stateMachine: DetailsStateMachine
): ReduxViewModel<DetailsState, DetailsAction>(stateMachine)