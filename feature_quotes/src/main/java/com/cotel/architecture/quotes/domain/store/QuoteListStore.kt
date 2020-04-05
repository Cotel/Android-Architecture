package com.cotel.architecture.quotes.domain.store

import com.cotel.architecture.base.infrastructure.PaginationState
import com.cotel.architecture.base.presentation.mvi.Intent
import com.cotel.architecture.base.presentation.mvi.Store
import com.cotel.architecture.quotes.domain.model.Quote
import com.cotel.architecture.quotes.domain.store.QuoteListStore.State

class QuoteListStore(initialState: State = State()) :
    Store<State>(initialState) {

    sealed class Intents : Intent<State> {
        data class FetchQuotes(val forceRefresh: Boolean = false) : Intents() {
            override fun reduce(oldState: State): State {
                val pagination = if (forceRefresh) PaginationState()
                else oldState.pagination

                return oldState.copy(pagination = pagination.isLoading())
            }
        }

        data class FetchQuotesError(val error: Throwable) : Intents() {
            override fun reduce(oldState: State): State =
                oldState.copy(
                    pagination = oldState.pagination.isNotLoading(),
                    hasError = true
                )
        }

        data class FetchQuotesSuccess(val quotes: List<Quote>) : Intents() {
            override fun reduce(oldState: State): State =
                oldState.copy(
                    pagination = oldState.pagination.nextPage().isNotLoading(),
                    allQuotes = oldState.allQuotes + quotes
                )
        }

        data class FetchSavedQuotesSuccess(val quotes: List<Quote>) :
            Intents() {
            override fun reduce(oldState: State): State =
                oldState.copy(
                    pagination = oldState.pagination.isNotLoading(),
                    savedQuotes = quotes
                )
        }

        data class ChangeFilter(val filter: Filter) : Intents() {
            override fun reduce(oldState: State): State =
                oldState.copy(
                    currentFilter = filter
                )
        }
    }

    data class State(
        val hasError: Boolean = false,
        val allQuotes: List<Quote> = emptyList(),
        val savedQuotes: List<Quote> = emptyList(),
        val currentFilter: Filter = Filter.DISCOVER,
        val pagination: PaginationState = PaginationState()
    )

    enum class Filter {
        DISCOVER, SAVED;
    }

}
