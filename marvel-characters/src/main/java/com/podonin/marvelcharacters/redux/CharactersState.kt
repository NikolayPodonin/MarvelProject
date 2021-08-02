package com.podonin.marvelcharacters.redux

import com.podonin.base_ui.ReduxState
import com.podonin.marvelcharacters.domain.model.MarvelCharacter

sealed class CharactersState: ReduxState

object EmptyLoading: CharactersState()

object EmptyError: CharactersState()

data class CharactersLoaded(
    val characters: List<MarvelCharacter>
): CharactersState()
