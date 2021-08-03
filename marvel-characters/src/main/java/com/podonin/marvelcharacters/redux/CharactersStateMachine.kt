package com.podonin.marvelcharacters.redux

import com.freeletics.flowredux.dsl.ChangeState
import com.freeletics.flowredux.dsl.MutateState
import com.freeletics.flowredux.dsl.OverrideState
import com.podonin.base_ui.ReduxStateMachine
import com.podonin.marvelcharacters.domain.CharactersInteractor
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class CharactersStateMachine(
    private val interactor: CharactersInteractor
) : ReduxStateMachine<CharactersState, CharactersAction>(
    initialState = EmptyLoading
) {
    init {
        spec {
            inState<EmptyLoading> {
                onEnter { loadCharacters() }
            }
            inState<EmptyError> {
                on<Reload> { _, _ -> OverrideState(EmptyLoading) }
            }
            inState<CharactersLoaded> {
                on<LoadNextPage> { _, state ->
                    OverrideState(
                        NewPageLoading(state.characters)
                    )
                }
                on<SelectCharacter> { action, _ -> selectCharacter(action = action) }
            }
            inState<NewPageLoading> {
                onEnter { state -> loadNextPage(state) }
                on<SelectCharacter> { action, _ -> selectCharacter(action = action) }
            }
        }
    }

    private fun selectCharacter(action: SelectCharacter): ChangeState<CharactersState> {
        return OverrideState(CharacterSelected(action.characterId))
    }

    private suspend fun loadCharacters(): ChangeState<CharactersState> {
        return try {
            val characters = interactor.getCharacters()
            OverrideState(CharactersLoaded(characters))
        } catch (ex: Throwable) {
            OverrideState(EmptyError)
        }
    }

    private suspend fun loadNextPage(state: WithCharacters): ChangeState<CharactersState> {
        return try {
            val newCharacters = interactor.getNextCharacters(state.characters.size)
            MutateState {
                CharactersLoaded(state.characters + newCharacters)
            }
        } catch (ex: Throwable) {
            MutateState { state }
        }
    }
}