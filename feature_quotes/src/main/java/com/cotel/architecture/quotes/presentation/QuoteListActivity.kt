package com.cotel.architecture.quotes.presentation

import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cotel.architecture.base.presentation.activity.BaseViewModelActivity
import com.cotel.architecture.base.presentation.extension.gone
import com.cotel.architecture.base.presentation.extension.visible
import com.cotel.architecture.base.presentation.recyclerview.EndlessScrollListener
import com.cotel.architecture.quotes.R
import com.cotel.architecture.quotes.presentation.QuoteListViewModel.SideEffect
import com.cotel.architecture.quotes.presentation.QuoteListViewModel.ViewState
import kotlinx.android.synthetic.main.activity_quote_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class QuoteListActivity :
    BaseViewModelActivity<ViewState, SideEffect, QuoteListViewModel>() {

    override val layoutResId: Int = R.layout.activity_quote_list

    override val viewModel: QuoteListViewModel by viewModel()

    private val adapter = QuoteListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        injectFeature()
        super.onCreate(savedInstanceState)

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
            viewModel.loadQuotes(forceRefresh = true)
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
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    private fun setupUI() {
        quotes_list_recyclerview.let {
            it.adapter = this.adapter
            it.layoutManager = LinearLayoutManager(this)
            it.addItemDecoration(
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
            )
            it.addOnScrollListener(EndlessScrollListener { viewModel.loadQuotes() })
        }
    }
}
