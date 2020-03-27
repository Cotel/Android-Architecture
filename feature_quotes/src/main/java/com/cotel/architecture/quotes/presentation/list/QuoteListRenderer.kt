package com.cotel.architecture.quotes.presentation.list

import androidx.annotation.LayoutRes
import com.cotel.architecture.quotes.domain.model.Quote

interface QuoteListRenderer {
    @LayoutRes
    fun getLayoutRes(): Int

    fun setup(fragment: QuoteListFragment, callbacks: Callbacks)
    fun renderState(viewState: QuoteListViewModel.ViewState)

    interface Callbacks {
        fun handleQuoteClicked(quote: Quote)
        fun handleLoadMorePages()
        fun handleDiscoverFilterPressed()
        fun handleSavedFilterPressed()
        fun handleRetryLoadingQuotes()
    }
}
