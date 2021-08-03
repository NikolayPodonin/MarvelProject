package com.podonin.marvelcharacters.redux

import com.podonin.base_ui.ReduxState
import com.podonin.marvelcharacters.domain.model.MarvelCharacter

sealed class CharactersState : ReduxState

object EmptyLoading : CharactersState()

object EmptyError : CharactersState()

data class CharacterSelected(
    val characterId: Int
) : CharactersState()

sealed class WithCharacters(
    open val characters: List<MarvelCharacter>
) : CharactersState()

data class CharactersLoaded(
    override val characters: List<MarvelCharacter>
) : WithCharacters(characters)

data class NewPageLoading(
    override val characters: List<MarvelCharacter>
) : WithCharacters(characters)
