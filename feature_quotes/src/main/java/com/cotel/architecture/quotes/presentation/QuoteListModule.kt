package com.cotel.architecture.quotes.presentation

import com.cotel.architecture.quotes.data.quoteListDataModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module

fun injectFeature() = loadFeature

fun ejectFeature() {
    unloadKoinModules(listOf(quoteListDataModule, quoteListModule))
}

private val loadFeature by lazy {
    loadKoinModules(listOf(quoteListDataModule, quoteListModule))
}

val quoteListModule = module {
    viewModel { QuoteListViewModel(repository = get()) }
}
