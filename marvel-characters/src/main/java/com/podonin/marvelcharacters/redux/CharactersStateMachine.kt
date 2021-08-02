package com.podonin.marvelcharacters.redux

import com.freeletics.flowredux.dsl.ChangeState
import com.freeletics.flowredux.dsl.OverrideState
import com.podonin.base_ui.ReduxStateMachine
import com.podonin.marvelcharacters.*
import com.podonin.marvelcharacters.domain.CharactersInteractor
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class CharactersStateMachine(
    private val interactor: CharactersInteractor
): ReduxStateMachine<CharactersState, CharactersAction>(
    initialState = EmptyLoading
) {
    init {
        spec {
            inState<EmptyLoading> {
                onEnter { loadCharacters() }
            }
        }
    }

    private suspend fun loadCharacters(): ChangeState<CharactersState> {
        return try {
            val characters = interactor.getCharacters()
            OverrideState(CharactersLoaded(characters))
        } catch (ex: Throwable) {
            OverrideState(EmptyError)
        }
    }
}