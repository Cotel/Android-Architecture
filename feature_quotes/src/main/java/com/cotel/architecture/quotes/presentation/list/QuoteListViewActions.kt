package com.cotel.architecture.quotes.presentation.list

import com.cotel.architecture.quotes.domain.model.Quote

sealed class QuoteListViewActions {
    data class QuoteClicked(val quote: Quote) : QuoteListViewActions()

    object StartScreen : QuoteListViewActions()
    object RetryFetchQuotesPressed : QuoteListViewActions()
    object ScrollEndReached : QuoteListViewActions()
    object DiscoverTabSelected : QuoteListViewActions()
    object SavedTabSelected : QuoteListViewActions()
}
