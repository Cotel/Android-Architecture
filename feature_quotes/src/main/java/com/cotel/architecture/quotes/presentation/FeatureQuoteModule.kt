package com.cotel.architecture.quotes.presentation

import com.cotel.architecture.quotes.data.chuckNorrisQuoteListDataModule
import com.cotel.architecture.quotes.data.programmingQuoteListDataModule
import com.cotel.architecture.quotes.presentation.list.QuoteListFragment
import com.cotel.architecture.quotes.presentation.list.QuoteListRenderer
import com.cotel.architecture.quotes.presentation.list.QuoteListViewModel
import com.cotel.architecture.quotes.presentation.list.chucknorris.ChuckNorrisQuoteListRenderer
import com.cotel.architecture.quotes.presentation.list.programming.ProgrammingQuoteListRenderer
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module

// Programming modules

fun injectProgrammingFeature() =
    loadKoinModules(allProgrammingModules)

private val programmingQuoteListModule = module {
    scope<QuoteListFragment> {
        scoped<QuoteListRenderer>(override = true) { ProgrammingQuoteListRenderer() }

        viewModel(override = true) {
            QuoteListViewModel(
                repository = get()
            )
        }
    }
}

val allProgrammingModules = listOf(
    programmingQuoteListDataModule,
    programmingQuoteListModule
)

// Chuck Norris modules

fun injectChuckNorrisFeature() =
    loadKoinModules(allChuckNorrisModules)

private val chuckNorrisQuoteListModule = module {
    scope<QuoteListFragment> {
        scoped<QuoteListRenderer>(override = true) { ChuckNorrisQuoteListRenderer() }

        viewModel(override = true) {
            QuoteListViewModel(
                repository = get()
            )
        }
    }
}

private val allChuckNorrisModules = listOf(
    chuckNorrisQuoteListDataModule,
    chuckNorrisQuoteListModule
)
