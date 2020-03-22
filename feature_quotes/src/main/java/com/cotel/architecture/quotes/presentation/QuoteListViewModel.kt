package com.cotel.architecture.quotes.presentation

import androidx.lifecycle.viewModelScope
import com.cotel.architecture.base.infrastructure.PaginationState
import com.cotel.architecture.base.presentation.viewmodel.BaseViewModel
import com.cotel.architecture.quotes.domain.model.Quote
import com.cotel.architecture.quotes.domain.repository.QuotesRepository
import com.cotel.architecture.quotes.presentation.QuoteListViewModel.SideEffect
import com.cotel.architecture.quotes.presentation.QuoteListViewModel.ViewState
import kotlinx.coroutines.launch

class QuoteListViewModel(
    private val repository: QuotesRepository
) : BaseViewModel<ViewState, SideEffect>() {

    private var paginationState = PaginationState()

    init {
        loadQuotes()
    }

    fun loadQuotes(forceRefresh: Boolean = false) {
        if (forceRefresh) paginationState = PaginationState()

        val allQuotes =
            (_viewState.value as? ViewState.DataLoaded)?.quotes ?: emptyList()

        if (paginationState.canRequestNextPage()) {
            viewModelScope.launch {
                paginationState = paginationState.isLoading()
                _viewState.value = ViewState.Loading
                repository.getQuotes(paginationState.page)
                    .fold(
                        ifLeft = {
                            paginationState =
                                paginationState.isNotLoading().noMorePages()
                            _viewState.value = ViewState.Error
                        },
                        ifRight = { quotes ->
                            paginationState =
                                paginationState.isNotLoading().nextPage()
                            _viewState.value =
                                ViewState.DataLoaded(allQuotes + quotes)
                        }
                    )
            }
        }
    }

    sealed class ViewState {
        object Loading : ViewState()
        object Error : ViewState()

        data class DataLoaded(val quotes: List<Quote>) : ViewState()
    }

    sealed class SideEffect
}
