package com.podonin.marvelcharacters.ui

import com.podonin.base_ui.ReduxViewModel
import com.podonin.marvelcharacters.redux.CharactersAction
import com.podonin.marvelcharacters.redux.CharactersState
import com.podonin.marvelcharacters.redux.CharactersStateMachine
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class CharactersViewModel(
    stateMachine: CharactersStateMachine
): ReduxViewModel<CharactersState, CharactersAction>(stateMachine)