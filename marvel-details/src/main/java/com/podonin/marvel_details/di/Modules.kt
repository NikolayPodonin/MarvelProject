package com.podonin.marvel_details.di

import com.podonin.marvel_details.domain.DetailsInteractor
import com.podonin.marvel_details.redux.DetailsStateMachine
import com.podonin.marvel_details.ui.DetailsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

@ExperimentalCoroutinesApi
fun getDetailsModules() = listOf(
    viewModelModule,
    domainModule,
    reduxModule
)

@ExperimentalCoroutinesApi
private val viewModelModule: Module = module {
    viewModel { characterId -> DetailsViewModel(get { characterId }) }
}

private val domainModule: Module = module {
    factory { DetailsInteractor() }
}

@ExperimentalCoroutinesApi
private val reduxModule: Module = module {
    factory { characterId -> DetailsStateMachine(characterId.get(), get()) }
}