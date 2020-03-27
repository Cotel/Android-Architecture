package com.cotel.architecture.quotes.presentation.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cotel.architecture.base.presentation.extension.gone
import com.cotel.architecture.base.presentation.extension.visible
import com.cotel.architecture.base.presentation.fragment.BaseViewModelFragment
import com.cotel.architecture.base.presentation.recyclerview.EndlessScrollListener
import com.cotel.architecture.quotes.R
import com.cotel.architecture.quotes.presentation.list.QuoteListViewModel.SideEffect
import com.cotel.architecture.quotes.presentation.list.QuoteListViewModel.ViewState
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_quote_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class QuoteListFragment : BaseViewModelFragment<ViewState,
    SideEffect,
    QuoteListViewModel>(R.layout.fragment_quote_list) {

    companion object {
        private const val DISCOVER_TAB_POSITION = 0
        private const val SAVED_TAB_POSITION = 1

        fun newInstance() = QuoteListFragment()
    }

    override val viewModel: QuoteListViewModel by viewModel()
    private val adapter: QuoteListAdapter by lazy {
        QuoteListAdapter(viewModel::handleQuoteClicked)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
    }

    override fun renderViewState(viewState: ViewState) {
        resetScreen()
        when (viewState) {
            ViewState.Loading -> renderLoading()
            ViewState.Error -> renderErrorLoading()
            is ViewState.DataLoaded -> renderDataLoaded(viewState)
        }
    }

    private fun renderLoading() {
        quotes_list_loader.visible()
    }

    private fun renderErrorLoading() {
        quotes_list_error.visible()
        quotes_list_retry_button.setOnClickListener {
            viewModel.loadAllQuotes(forceRefresh = true)
        }
    }

    private fun resetScreen() {
        quotes_list_loader.gone()
        quotes_list_error.gone()
    }

    private fun renderDataLoaded(viewState: ViewState.DataLoaded) {
        adapter.submitList(viewState.quotes)
    }

    override fun handleSideEffect(sideEffect: SideEffect) {
        when (sideEffect) {
            is SideEffect.ErrorSavingQuote -> showErrorSaveQuoteToast()
            is SideEffect.ErrorRemovingQuote -> showErrorRemoveQuoteToast()
        }
    }

    private fun setupUI() {
        quotes_list_recyclerview.let {
            it.adapter = this.adapter
            it.layoutManager = LinearLayoutManager(requireContext())
            it.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            it.addOnScrollListener(
                EndlessScrollListener { viewModel.loadAllQuotes() }
            )
        }

        quotes_tabs.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) {}
            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    DISCOVER_TAB_POSITION -> viewModel
                        .handleDiscoverFilterPressed()
                    SAVED_TAB_POSITION -> viewModel
                        .handleSavedFilterPressed()
                }
            }
        })
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
