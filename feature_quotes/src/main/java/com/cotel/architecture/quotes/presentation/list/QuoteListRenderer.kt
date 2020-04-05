package com.cotel.architecture.quotes.presentation.list

import androidx.annotation.LayoutRes
import com.cotel.architecture.quotes.domain.model.Quote
import kotlinx.coroutines.flow.Flow

interface QuoteListRenderer {
    @LayoutRes
    fun getLayoutRes(): Int

    fun setup(fragment: QuoteListFragment)
    fun renderState(viewState: QuoteListViewState)

    fun actions(): Flow<QuoteListViewActions>

}
