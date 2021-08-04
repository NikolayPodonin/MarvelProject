package com.podonin.marvel_details.redux

import com.freeletics.flowredux.dsl.ChangeState
import com.freeletics.flowredux.dsl.OverrideState
import com.podonin.base_ui.ReduxStateMachine
import com.podonin.marvel_details.domain.DetailsInteractor
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class DetailsStateMachine(
    private val characterId: String,
    private val interactor: DetailsInteractor
) : ReduxStateMachine<DetailsState, DetailsAction>(
    initialState = EmptyLoading
) {
    init {
        spec {
            inState<EmptyLoading> {
                onEnter { loadCharacter() }
            }
            inState<EmptyError> {
                on<Reload> { _, _ -> OverrideState(EmptyLoading) }
            }
        }
    }

    private suspend fun loadCharacter(): ChangeState<DetailsState> {
        return try {
            val characters = interactor.getCharacter(characterId)
            OverrideState(DetailsLoaded(characters))
        } catch (ex: Throwable) {
            OverrideState(EmptyError)
        }
    }
}