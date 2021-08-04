package com.podonin.marvel_details.redux

import com.podonin.base_ui.ReduxState
import com.podonin.marvel_details.domain.model.FullCharacter

sealed class DetailsState : ReduxState

object EmptyLoading : DetailsState()

object EmptyError : DetailsState()

data class DetailsLoaded(
    val character: FullCharacter
) : DetailsState()
