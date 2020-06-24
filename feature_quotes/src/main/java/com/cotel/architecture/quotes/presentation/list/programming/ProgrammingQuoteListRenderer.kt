package com.cotel.architecture.quotes.presentation.list.programming

import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cotel.architecture.base.presentation.extension.gone
import com.cotel.architecture.base.presentation.extension.visible
import com.cotel.architecture.base.presentation.recyclerview.EndlessScrollListener
import com.cotel.architecture.quotes.R
import com.cotel.architecture.quotes.di.QuotesListScope
import com.cotel.architecture.quotes.presentation.list.QuoteListFragment
import com.cotel.architecture.quotes.presentation.list.QuoteListRenderer
import com.cotel.architecture.quotes.presentation.list.QuoteListViewModel.ViewState
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_programming_quote_list.*
import javax.inject.Inject

@QuotesListScope
class ProgrammingQuoteListRenderer @Inject constructor() : QuoteListRenderer {
    companion object {
        private const val DISCOVER_TAB_POSITION = 0
        private const val SAVED_TAB_POSITION = 1
    }

    private lateinit var fragment: QuoteListFragment
    private lateinit var callbacks: QuoteListRenderer.Callbacks

    private val adapter: ProgrammingQuoteListAdapter by lazy {
        ProgrammingQuoteListAdapter(
            callbacks::handleQuoteClicked
        )
    }

    @LayoutRes
    override fun getLayoutRes(): Int = R.layout.fragment_programming_quote_list

    override fun setup(
        fragment: QuoteListFragment,
        callbacks: QuoteListRenderer.Callbacks
    ) {
        this.fragment = fragment
        this.callbacks = callbacks

        with(fragment) {
            setupUI()
        }
    }

    override fun renderState(viewState: ViewState) {
        with(fragment) {
            resetScreen()
            when (viewState) {
                ViewState.Loading -> renderLoading()
                ViewState.Error -> renderErrorLoading()
                is ViewState.DataLoaded -> renderDataLoaded(
                    viewState
                )
            }
        }
    }

    private fun QuoteListFragment.renderLoading() {
        quotes_list_loader.visible()
    }

    private fun QuoteListFragment.renderErrorLoading() {
        quotes_list_error.visible()
        quotes_list_retry_button.setOnClickListener {
            callbacks.handleRetryLoadingQuotes()
        }
    }

    private fun QuoteListFragment.resetScreen() {
        quotes_list_loader.gone()
        quotes_list_error.gone()
    }

    private fun renderDataLoaded(viewState: ViewState.DataLoaded) {
        adapter.submitList(viewState.quotes)
    }

    private fun QuoteListFragment.setupUI() {
        quotes_list_recyclerview.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
            it.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            it.addOnScrollListener(
                EndlessScrollListener { callbacks.handleLoadMorePages() }
            )
        }

        quotes_tabs.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) {}
            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    DISCOVER_TAB_POSITION -> callbacks
                        .handleDiscoverFilterPressed()
                    SAVED_TAB_POSITION -> callbacks
                        .handleSavedFilterPressed()
                }
            }
        })
    }
}
