package com.cotel.architecture.quotes.presentation.list.chucknorris

import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import coil.api.load
import com.cotel.architecture.base.presentation.extension.gone
import com.cotel.architecture.base.presentation.extension.visible
import com.cotel.architecture.quotes.R
import com.cotel.architecture.quotes.domain.model.Quote
import com.cotel.architecture.quotes.presentation.list.QuoteListFragment
import com.cotel.architecture.quotes.presentation.list.QuoteListRenderer
import kotlinx.android.synthetic.main.fragment_chucknorris_quote_list.*

class ChuckNorrisQuoteListRenderer : QuoteListRenderer {
    companion object {
        private const val CN_LOADING_IMAGE_URL =
            "https://www.quien.net/wp-content/uploads/Chuck-Norris-241x300.jpg"

        private const val CN_ERROR_IMAGE_URL =
            "https://vignette.wikia.nocookie.net/character-stats-and-profiles" +
                "/images/7/78/ChuckNorris.png/revision/latest" +
                "/scale-to-width-down/300?cb=20190602184133"
    }

    private lateinit var fragment: QuoteListFragment
    private lateinit var callbacks: QuoteListRenderer.Callbacks

    private val adapter: ChuckNorrisQuoteListAdapter by lazy {
        ChuckNorrisQuoteListAdapter()
    }

    @LayoutRes
    override fun getLayoutRes(): Int = R.layout.fragment_chucknorris_quote_list

    override fun setup(
        fragment: QuoteListFragment,
        callbacks: QuoteListRenderer.Callbacks
    ) {
        this.fragment = fragment
        this.callbacks = callbacks

        fragment.setupUI()
    }

    override fun renderState(viewState: QuoteListViewModel.ViewState) {
        with(fragment) {
            resetScreen()

            when (viewState) {
                QuoteListViewModel.ViewState.Loading -> showLoading()
                QuoteListViewModel.ViewState.Error -> showError()
                is QuoteListViewModel.ViewState.DataLoaded ->
                    showQuotes(viewState.quotes)
            }
        }
    }

    private fun QuoteListFragment.resetScreen() {
        cn_quotes_loading.gone()
    }

    private fun QuoteListFragment.showLoading() {
        cn_quotes_loading.load(CN_LOADING_IMAGE_URL)
        cn_quotes_loading.visible()
    }

    private fun QuoteListFragment.showError() {
        cn_quotes_loading.load(CN_ERROR_IMAGE_URL)
        cn_quotes_loading.visible()
    }

    private fun QuoteListFragment.showQuotes(quotes: List<Quote>) {
        adapter.submitList(quotes)
    }

    private fun QuoteListFragment.setupUI() {
        cn_quotes_list_recyclerview.adapter = adapter
        cn_quotes_list_recyclerview.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(cn_quotes_list_recyclerview)
    }
}
