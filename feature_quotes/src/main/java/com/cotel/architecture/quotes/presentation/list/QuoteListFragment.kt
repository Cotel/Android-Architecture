package com.cotel.architecture.quotes.presentation.list

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.lifecycle.lifecycleScope
import arrow.core.getOrHandle
import arrow.fx.IO
import arrow.integrations.kotlinx.unsafeRunScoped
import com.cotel.architecture.base.presentation.fragment.BaseViewModelFragment
import com.cotel.architecture.quotes.domain.model.Quote
import com.cotel.architecture.quotes.presentation.list.QuoteListViewModel.SideEffect
import com.cotel.architecture.quotes.presentation.list.QuoteListViewModel.ViewState
import org.koin.androidx.viewmodel.scope.viewModel
import org.koin.androidx.scope.lifecycleScope as koinScope

class QuoteListFragment : BaseViewModelFragment<ViewState,
    SideEffect,
    QuoteListViewModel>(),
    QuoteListRenderer.Callbacks {

    companion object {
        fun newInstance() = QuoteListFragment()
    }

    override val viewModel: QuoteListViewModel by koinScope.viewModel(this)
    private val renderer: QuoteListRenderer by koinScope.inject()

    @LayoutRes
    override fun layoutRes(): Int = renderer.getLayoutRes()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        renderer.setup(this, this)

        viewModel.loadAllQuotes(true)
            .unsafeRunScoped()
    }

    override fun renderViewState(viewState: ViewState) {
        renderer.renderState(viewState)
    }

    override fun handleQuoteClicked(quote: Quote) {
        viewModel.handleQuoteClicked(quote)
            .unsafeRunScoped()
    }

    override fun handleLoadMorePages() {
        viewModel.loadAllQuotes()
            .unsafeRunScoped()
    }

    override fun handleDiscoverFilterPressed() {
        viewModel.handleDiscoverFilterPressed()
            .unsafeRunScoped()
    }

    override fun handleSavedFilterPressed() {
        viewModel.handleSavedFilterPressed()
            .unsafeRunScoped()
    }

    override fun handleRetryLoadingQuotes() {
        viewModel.loadAllQuotes(forceRefresh = true)
            .unsafeRunScoped()
    }

    override fun handleSideEffect(sideEffect: SideEffect) {
        when (sideEffect) {
            is SideEffect.ErrorSavingQuote -> showErrorSaveQuoteToast()
            is SideEffect.ErrorRemovingQuote -> showErrorRemoveQuoteToast()
        }
    }

    private fun showErrorSaveQuoteToast() {
        Toast.makeText(
            requireContext(),
            "Fail while saving quote",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showErrorRemoveQuoteToast() {
        Toast.makeText(
            requireContext(),
            "Fail while removing quote",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun IO<Unit>.unsafeRunScoped() =
        unsafeRunScoped(lifecycleScope) { execution ->
            execution.getOrHandle {
                Log.e("QuoteListFragment", "FATAL ERROR", it)
            }
        }
}
