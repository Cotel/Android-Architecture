package com.cotel.architecture.quotes.presentation

import com.cotel.architecture.quotes.data.chuckNorrisQuoteListDataModule
import com.cotel.architecture.quotes.data.programmingQuoteListDataModule
import com.cotel.architecture.quotes.presentation.list.QuoteListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module

// Common modules

private val quoteListModule = module {
    viewModel {
        QuoteListViewModel(
            repository = get()
        )
    }
}

// Programming modules

fun injectProgrammingFeature() =
    loadProgrammingFeature

val allProgrammingModules = listOf(
    programmingQuoteListDataModule,
    quoteListModule
)

fun ejectProgrammingFeature() {
    unloadKoinModules(allProgrammingModules)
}

private val loadProgrammingFeature by lazy {
    loadKoinModules(allProgrammingModules)
}

// Chuck Norris modules

private val allChuckNorrisModules = listOf(
    chuckNorrisQuoteListDataModule,
    quoteListModule
)

fun injectChuckNorrisFeature() =
    loadChuckNorrisFeature

fun ejectChuckNorrisFeature() {
    unloadKoinModules(allChuckNorrisModules)
}

private val loadChuckNorrisFeature by lazy {
    loadKoinModules(allChuckNorrisModules)
}
