package com.podonin.marvelcharacters.redux

import com.podonin.base_ui.ReduxAction

sealed class CharactersAction: ReduxAction

object Reload: CharactersAction()


