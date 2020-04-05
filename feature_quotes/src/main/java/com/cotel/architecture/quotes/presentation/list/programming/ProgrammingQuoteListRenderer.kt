package com.cotel.architecture.quotes.presentation.list.programming

import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cotel.architecture.base.presentation.extension.clicks
import com.cotel.architecture.base.presentation.extension.endlessScroll
import com.cotel.architecture.base.presentation.extension.gone
import com.cotel.architecture.base.presentation.extension.safeOffer
import com.cotel.architecture.base.presentation.extension.visible
import com.cotel.architecture.quotes.R
import com.cotel.architecture.quotes.domain.model.Quote
import com.cotel.architecture.quotes.extensions.tabSelections
import com.cotel.architecture.quotes.presentation.list.QuoteListFragment
import com.cotel.architecture.quotes.presentation.list.QuoteListRenderer
import com.cotel.architecture.quotes.presentation.list.QuoteListViewActions
import com.cotel.architecture.quotes.presentation.list.QuoteListViewState
import kotlinx.android.synthetic.main.fragment_programming_quote_list.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.map

class ProgrammingQuoteListRenderer : QuoteListRenderer {
    companion object {
        private const val DISCOVER_TAB_POSITION = 0
        private const val SAVED_TAB_POSITION = 1
    }

    private lateinit var fragment: QuoteListFragment

    private val quoteListItemClicks = ConflatedBroadcastChannel<Quote>()

    private val adapter: ProgrammingQuoteListAdapter by lazy {
        ProgrammingQuoteListAdapter { quote ->
            quoteListItemClicks.safeOffer(quote)
        }
    }

    @LayoutRes
    override fun getLayoutRes(): Int = R.layout.fragment_programming_quote_list

    override fun setup(fragment: QuoteListFragment) {
        this.fragment = fragment

        with(fragment) {
            setupUI()
        }
    }

    override fun renderState(viewState: QuoteListViewState) {
        with(fragment) {
            resetScreen()
            when (viewState) {
                QuoteListViewState.Loading -> renderLoading()
                QuoteListViewState.Error -> renderErrorLoading()
                is QuoteListViewState.Loaded -> renderDataLoaded(
                    viewState
                )
            }
        }
    }

    override fun actions(): Flow<QuoteListViewActions> {
        with (fragment) {
            val flows = listOf(
                quotes_list_retry_button.clicks()
                    .map { QuoteListViewActions.RetryFetchQuotesPressed },
                quotes_list_recyclerview.endlessScroll()
                    .map { QuoteListViewActions.ScrollEndReached },
                quotes_tabs.tabSelections()
                    .map { tab -> when (tab.position) {
                        SAVED_TAB_POSITION -> QuoteListViewActions.SavedTabSelected
                        else -> QuoteListViewActions.DiscoverTabSelected
                    } },
                quoteListItemClicks.asFlow().map { quote ->
                    QuoteListViewActions.QuoteClicked(quote)
                }
            )

            return flows.asFlow().flattenMerge(flows.size)
        }
    }

    private fun QuoteListFragment.renderLoading() {
        quotes_list_loader.visible()
    }

    private fun QuoteListFragment.renderErrorLoading() {
        quotes_list_error.visible()
    }

    private fun QuoteListFragment.resetScreen() {
        quotes_list_loader.gone()
        quotes_list_error.gone()
    }

    private fun renderDataLoaded(viewState: QuoteListViewState.Loaded) {
        adapter.submitList(viewState.content)
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
        }
    }
}
