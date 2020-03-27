package com.cotel.architecture.quotes.presentation.list

import androidx.lifecycle.viewModelScope
import com.cotel.architecture.base.infrastructure.PaginationState
import com.cotel.architecture.base.presentation.viewmodel.BaseViewModel
import com.cotel.architecture.quotes.domain.model.Quote
import com.cotel.architecture.quotes.domain.repository.QuotesRepository
import com.cotel.architecture.quotes.presentation.list.QuoteListViewModel.SideEffect
import com.cotel.architecture.quotes.presentation.list.QuoteListViewModel.ViewState
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class QuoteListViewModel(
    private val repository: QuotesRepository
) : BaseViewModel<ViewState, SideEffect>() {

    private var internalState: QuotesState by Delegates
        .observable(QuotesState()) { _, _, newValue ->
            if (newValue.pagination.isLoading) {
                _viewState.value = ViewState.Loading
                return@observable
            }

            if (newValue.hasError) {
                _viewState.value = ViewState.Error
                return@observable
            }

            when (newValue.currentFilter) {
                Filter.DISCOVER -> _viewState.value = ViewState
                    .DataLoaded(newValue.allQuotes)
                Filter.SAVED -> _viewState.value = ViewState
                    .DataLoaded(newValue.savedQuotes)
            }
        }

    init {
        loadAllQuotes()
    }

    fun loadAllQuotes(forceRefresh: Boolean = false) {
        if (internalState.currentFilter == Filter.SAVED) return

        if (forceRefresh) {
            internalState = internalState.copy(pagination = PaginationState())
        }

        val pagination = internalState.pagination

        if (pagination.canRequestNextPage()) {
            viewModelScope.launch {
                internalState = internalState
                    .copy(pagination = pagination.isLoading())
                repository.getQuotes(pagination.page)
                    .attempt()
                    .unsafeRunSync()
                    .fold(
                        ifLeft = { handlePageLoadError() },
                        ifRight = this@QuoteListViewModel::handlePageLoadSuccess
                    )
            }
        }
    }

    private fun loadSavedQuotes() {
        viewModelScope.launch {
            repository.getSavedQuotes()
                .attempt()
                .unsafeRunSync()
                .fold(
                    ifLeft = { },
                    ifRight = {
                        internalState = internalState.copy(savedQuotes = it)
                    }
                )
        }
    }

    fun handleDiscoverFilterPressed() {
        internalState = internalState.copy(currentFilter = Filter.DISCOVER)
    }

    fun handleSavedFilterPressed() {
        internalState = internalState.copy(currentFilter = Filter.SAVED)
        loadSavedQuotes()
    }

    fun handleQuoteClicked(quote: Quote) {
        viewModelScope.launch {
            if (!quote.isSaved) {
                repository.saveQuote(quote)
                    .attempt()
                    .unsafeRunSync()
                    .fold(
                        ifLeft = {
                            _sideEffects
                                .send(SideEffect.ErrorSavingQuote(quote))
                        },
                        ifRight = {
                            val copy = quote.copy(isSaved = true)
                            updateQuote(copy)
                        }
                    )
            } else {
                repository.removeQuote(quote)
                    .attempt()
                    .unsafeRunSync()
                    .fold(
                        ifLeft = {
                            _sideEffects
                                .send(SideEffect.ErrorRemovingQuote(quote))
                        },
                        ifRight = {
                            val copy = quote.copy(isSaved = false)
                            updateQuote(copy)
                        }
                    )
            }
        }
    }

    private fun handlePageLoadSuccess(quotes: List<Quote>) {
        val pagination = internalState.pagination
        val allQuotes = internalState.allQuotes

        internalState = internalState.copy(
            pagination = pagination
                .isNotLoading()
                .nextPage(),
            allQuotes = allQuotes + quotes
        )
    }

    private fun handlePageLoadError() {
        val pagination = internalState.pagination

        internalState = internalState.copy(
            pagination = pagination
                .isNotLoading()
                .noMorePages(),
            hasError = true
        )
    }

    private fun updateQuote(quote: Quote) {
        internalState = internalState.copy(
            allQuotes = internalState
                .allQuotes
                .map {
                    if (it.id == quote.id) quote else it
                }
        )
    }

    sealed class ViewState {
        object Loading : ViewState()
        object Error : ViewState()

        data class DataLoaded(val quotes: List<Quote>) : ViewState()
    }

    private data class QuotesState(
        val hasError: Boolean = false,
        val allQuotes: List<Quote> = emptyList(),
        val savedQuotes: List<Quote> = emptyList(),
        val currentFilter: Filter = Filter.DISCOVER,
        val pagination: PaginationState = PaginationState()
    )

    sealed class SideEffect {
        data class ErrorSavingQuote(val quote: Quote) : SideEffect()
        data class ErrorRemovingQuote(val quote: Quote) : SideEffect()
    }

    private enum class Filter {
        DISCOVER, SAVED;
    }
}
