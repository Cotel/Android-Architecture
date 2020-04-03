package com.cotel.architecture.quotes.presentation.list

import com.cotel.architecture.base.infrastructure.PaginationState
import com.cotel.architecture.base.presentation.mvi.Observer
import com.cotel.architecture.base.presentation.mvi.Reducer
import com.cotel.architecture.base.presentation.mvi.Store
import com.cotel.architecture.quotes.domain.model.Quote
import com.cotel.architecture.quotes.presentation.list.QuoteListStore.Action
import com.cotel.architecture.quotes.presentation.list.QuoteListStore.State

private fun List<Quote>.updateQuote(quote: Quote) =
    map { if (it.id == quote.id) quote else it }

private val reducer: Reducer<State, Action> = { action, currentState ->
    when (action) {
        is Action.FetchQuotes -> {
            val pagination =
                if (action.refresh) PaginationState()
                else currentState.pagination
            currentState.copy(
                pagination = pagination.isLoading(),
                hasError = false
            )
        }
        is Action.FetchQuotesSuccess -> currentState.copy(
            pagination = currentState.pagination.nextPage().isNotLoading(),
            allQuotes = currentState.allQuotes + action.content
        )
        is Action.FetchSavedQuotesSuccess -> currentState.copy(
            savedQuotes = action.content
        )
        is Action.FetchQuotesError -> currentState.copy(
            pagination = currentState.pagination.isNotLoading(),
            hasError = true
        )
        is Action.ChangeQuotesFilter -> currentState.copy(
            currentFilter = action.filter
        )
        is Action.SaveQuote -> currentState.copy(
            allQuotes = currentState.allQuotes
                .updateQuote(action.quote.copy(isSaved = true))
        )
        is Action.UnsaveQuote -> currentState.copy(
            allQuotes = currentState.allQuotes
                .updateQuote(action.quote.copy(isSaved = false))
        )
        else -> currentState
    }
}

class QuoteListStore(
    initialState: State = State(),
    observer: Observer<State>
) : Store<State, Action>(
    initialState,
    reducer,
    observer
) {

    sealed class Action {
        data class FetchQuotes(val refresh: Boolean = false) : Action()
        object FetchSavedQuotes : Action()
        data class FetchQuotesSuccess(val content: List<Quote>) : Action()
        data class FetchSavedQuotesSuccess(val content: List<Quote>) : Action()
        data class FetchQuotesError(val error: Throwable) : Action()
        data class ChangeQuotesFilter(val filter: Filter) : Action()
        data class SaveQuote(val quote: Quote): Action()
        data class UnsaveQuote(val quote: Quote): Action()
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
