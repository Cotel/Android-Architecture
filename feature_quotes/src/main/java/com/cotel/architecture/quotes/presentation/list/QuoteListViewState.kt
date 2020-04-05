package com.cotel.architecture.quotes.presentation.list

import com.cotel.architecture.quotes.domain.model.Quote
import com.cotel.architecture.quotes.domain.store.QuoteListStore

sealed class QuoteListViewState {
    object Loading : QuoteListViewState()
    object Error : QuoteListViewState()

    data class Loaded(val content: List<Quote>) : QuoteListViewState()
}

fun QuoteListStore.State.toViewState(): QuoteListViewState {
    if (this.pagination.isLoading)
        return QuoteListViewState.Loading

    if (this.hasError)
        return QuoteListViewState.Error

    return when (this.currentFilter) {
        QuoteListStore.Filter.DISCOVER -> QuoteListViewState.Loaded(allQuotes)
        QuoteListStore.Filter.SAVED -> QuoteListViewState.Loaded(savedQuotes)
    }
}
