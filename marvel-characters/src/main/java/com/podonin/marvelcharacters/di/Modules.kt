package com.podonin.marvelcharacters.di

import com.podonin.marvelcharacters.domain.CharactersInteractor
import com.podonin.marvelcharacters.redux.CharactersStateMachine
import com.podonin.marvelcharacters.ui.CharactersViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.factory
import org.koin.dsl.module

@ExperimentalCoroutinesApi
fun getCharactersModules() = listOf(
    viewModelModule,
    domainModule,
    reduxModule
)

@ExperimentalCoroutinesApi
private val viewModelModule: Module = module {
    viewModel<CharactersViewModel>()
}

private val domainModule: Module = module {
    factory { CharactersInteractor() }
}

@ExperimentalCoroutinesApi
private val reduxModule: Module = module {
    factory<CharactersStateMachine>()
}