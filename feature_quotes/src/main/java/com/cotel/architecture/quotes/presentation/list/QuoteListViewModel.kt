package com.cotel.architecture.quotes.presentation.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.getOrHandle
import arrow.fx.IO
import arrow.integrations.kotlinx.unsafeRunScoped
import com.cotel.architecture.base.presentation.mvi.Intent
import com.cotel.architecture.base.presentation.mvi.sideEffect
import com.cotel.architecture.quotes.domain.repository.QuotesRepository
import com.cotel.architecture.quotes.domain.store.QuoteListStore
import com.cotel.architecture.quotes.domain.store.QuoteListStore.Intents
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class QuoteListViewModel(
    private val store: QuoteListStore,
    private val repository: QuotesRepository
) : ViewModel() {

    val viewState: Flow<QuoteListViewState>
        get() = store
            .latestState()
            .map { state -> state.toViewState() }

    suspend fun handleViewAction(viewAction: QuoteListViewActions) {
        store.dispatch(viewAction.toIntent())
    }

    private fun QuoteListViewActions.toIntent(): Intent<QuoteListStore.State> =
        when (this) {
            QuoteListViewActions.StartScreen ->
                buildFetchQuotesIntent(true)
            QuoteListViewActions.ScrollEndReached ->
                buildFetchQuotesIntent(false)
            is QuoteListViewActions.QuoteClicked -> TODO()
            QuoteListViewActions.RetryFetchQuotesPressed -> TODO()
            QuoteListViewActions.DiscoverTabSelected -> TODO()
            QuoteListViewActions.SavedTabSelected -> TODO()
        }

    private fun buildFetchQuotesIntent(forceRefresh: Boolean):
        Intent<QuoteListStore.State> = sideEffect {
        if (!pagination.canRequestNextPage()) return@sideEffect

        IO.effect { store.dispatch(Intents.FetchQuotes(forceRefresh)) }
            .flatMap { repository.getQuotes(pagination.page) }
            .attempt()
            .flatMap { result ->
                IO.effect {
                    result.fold(
                        { store.dispatch(Intents.FetchQuotesError(it)) },
                        { store.dispatch(Intents.FetchQuotesSuccess(it)) }
                    )
                }
            }
            .unsafeRunScoped(viewModelScope) { execution ->
                execution.getOrHandle {
                    Log.e("QuoteListViewModel", "FATAL ERROR", it)
                }
            }
    }
}
