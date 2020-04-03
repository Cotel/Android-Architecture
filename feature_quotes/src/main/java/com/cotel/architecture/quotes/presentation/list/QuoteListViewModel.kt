package com.cotel.architecture.quotes.presentation.list

import arrow.fx.IO
import arrow.fx.extensions.fx
import com.cotel.architecture.base.presentation.viewmodel.BaseViewModel
import com.cotel.architecture.quotes.domain.model.Quote
import com.cotel.architecture.quotes.domain.repository.QuotesRepository
import com.cotel.architecture.quotes.presentation.list.QuoteListStore.Action
import com.cotel.architecture.quotes.presentation.list.QuoteListStore.Filter
import com.cotel.architecture.quotes.presentation.list.QuoteListViewModel.SideEffect
import com.cotel.architecture.quotes.presentation.list.QuoteListViewModel.ViewState

class QuoteListViewModel(
    private val repository: QuotesRepository
) : BaseViewModel<ViewState, SideEffect>() {

    private val store = QuoteListStore { state ->
        if (state.pagination.isLoading) {
            _viewState.value = ViewState.Loading
            return@QuoteListStore
        }

        if (state.hasError) {
            _viewState.value = ViewState.Error
            return@QuoteListStore
        }

        when (state.currentFilter) {
            Filter.DISCOVER -> _viewState.value = ViewState
                .DataLoaded(state.allQuotes)
            Filter.SAVED -> _viewState.value = ViewState
                .DataLoaded(state.savedQuotes)
        }
    }

    fun loadAllQuotes(forceRefresh: Boolean = false): IO<Unit> =
        IO.fx {
            var currentState = store.getCurrentState().bind()

            if (currentState.currentFilter == Filter.SAVED)
                return@fx

            if (!currentState.pagination.canRequestNextPage())
                return@fx


            !effect { store.dispatch(Action.FetchQuotes(forceRefresh)) }
            currentState = store.getCurrentState().bind()

            val result = repository.getQuotes(currentState.pagination.page)
                .attempt()
                .bind()

            result.fold(
                ifLeft = {
                    !effect {
                        store.dispatch(Action.FetchQuotesError(it))
                    }
                },
                ifRight = {
                    !effect {
                        store.dispatch(Action.FetchQuotesSuccess(it))
                    }
                }
            )
        }

    private fun loadSavedQuotes(): IO<Unit> =
        IO.effect { store.dispatch(Action.FetchSavedQuotes) }
            .flatMap { repository.getSavedQuotes() }
            .flatMap {
                IO.effect {
                    store.dispatch(Action.FetchSavedQuotesSuccess(it))
                }
            }


    fun handleDiscoverFilterPressed(): IO<Unit> =
        IO.effect { store.dispatch(Action.ChangeQuotesFilter(Filter.DISCOVER)) }


    fun handleSavedFilterPressed(): IO<Unit> =
        IO.effect { store.dispatch(Action.ChangeQuotesFilter(Filter.SAVED)) }
            .flatMap { loadSavedQuotes() }

    fun handleQuoteClicked(quote: Quote): IO<Unit> =
        if (!quote.isSaved) {
            handleQuoteClicked(quote,
                { repository.saveQuote(it) },
                { _sideEffects.send(SideEffect.ErrorSavingQuote(quote)) },
                { store.dispatch(Action.SaveQuote(quote)) }
            )
        } else {
            handleQuoteClicked(quote,
                { repository.removeQuote(it) },
                { _sideEffects.send(SideEffect.ErrorRemovingQuote(quote)) },
                { store.dispatch(Action.UnsaveQuote(quote)) }
            )
        }

    private fun handleQuoteClicked(
        quote: Quote,
        quoteOperation: (Quote) -> IO<Unit>,
        onQuoteOperationError: suspend (Throwable) -> Unit,
        onQuoteOperationSuccess: suspend () -> Unit
    ): IO<Unit> =
        quoteOperation(quote)
            .attempt()
            .flatMap { result ->
                result.fold(
                    { IO.effect { onQuoteOperationError(it) } },
                    { IO.effect { onQuoteOperationSuccess() } }
                )
            }

    sealed class ViewState {
        object Loading : ViewState()
        object Error : ViewState()

        data class DataLoaded(val quotes: List<Quote>) : ViewState()
    }


    sealed class SideEffect {
        data class ErrorSavingQuote(val quote: Quote) : SideEffect()
        data class ErrorRemovingQuote(val quote: Quote) : SideEffect()
    }

}
