package com.podonin.marvel_details.redux

import com.podonin.base_ui.ReduxAction

sealed class DetailsAction: ReduxAction

object Reload: DetailsAction()