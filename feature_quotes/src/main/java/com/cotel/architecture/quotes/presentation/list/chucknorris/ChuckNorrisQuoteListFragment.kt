package com.cotel.architecture.quotes.presentation.list.chucknorris

import android.content.Context
import com.cotel.architecture.quotes.di.chucknorris.ChuckNorrisQuotesModule
import com.cotel.architecture.quotes.di.chucknorris.DaggerChuckNorrisQuotesListComponent
import com.cotel.architecture.quotes.presentation.list.QuoteListFragment

class ChuckNorrisQuoteListFragment : QuoteListFragment() {
    companion object {
        fun newInstance(): QuoteListFragment = ChuckNorrisQuoteListFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerChuckNorrisQuotesListComponent.builder()
            .chuckNorrisQuotesModule(ChuckNorrisQuotesModule())
            .build()
            .inject(this)
    }

}
