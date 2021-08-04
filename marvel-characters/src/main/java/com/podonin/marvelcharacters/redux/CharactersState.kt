package com.podonin.marvelcharacters.redux

import com.podonin.base_ui.ReduxState
import com.podonin.marvelcharacters.domain.model.SimpleCharacter

sealed class CharactersState : ReduxState

object EmptyLoading : CharactersState()

object EmptyError : CharactersState()

sealed class WithCharacters(
    open val characters: List<SimpleCharacter>
) : CharactersState()

data class CharactersLoaded(
    override val characters: List<SimpleCharacter>
) : WithCharacters(characters)

data class NewPageLoading(
    override val characters: List<SimpleCharacter>
) : WithCharacters(characters)
