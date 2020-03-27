package com.cotel.architecture.quotes.presentation.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import com.cotel.architecture.base.presentation.fragment.BaseViewModelFragment
import com.cotel.architecture.quotes.R
import com.cotel.architecture.quotes.domain.model.Quote
import com.cotel.architecture.quotes.presentation.list.QuoteListViewModel.SideEffect
import com.cotel.architecture.quotes.presentation.list.QuoteListViewModel.ViewState
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.androidx.viewmodel.scope.viewModel

class QuoteListFragment : BaseViewModelFragment<ViewState,
    SideEffect,
    QuoteListViewModel>(),
    QuoteListRenderer.Callbacks {

    companion object {
        fun newInstance() = QuoteListFragment()
    }

    override val viewModel: QuoteListViewModel by lifecycleScope.viewModel(this)
    private val renderer: QuoteListRenderer by lifecycleScope.inject()

    @LayoutRes
    override fun layoutRes(): Int = renderer.getLayoutRes()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        renderer.setup(this, this)
    }

    override fun renderViewState(viewState: ViewState) {
        renderer.renderState(viewState)
    }

    override fun handleQuoteClicked(quote: Quote) {
        viewModel.handleQuoteClicked(quote)
    }

    override fun handleLoadMorePages() {
        viewModel.loadAllQuotes()
    }

    override fun handleDiscoverFilterPressed() {
        viewModel.handleDiscoverFilterPressed()
    }

    override fun handleSavedFilterPressed() {
        viewModel.handleSavedFilterPressed()
    }

    override fun handleRetryLoadingQuotes() {
        viewModel.loadAllQuotes(forceRefresh = true)
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
}
