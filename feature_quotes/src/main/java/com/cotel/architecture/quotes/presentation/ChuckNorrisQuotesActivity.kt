package com.cotel.architecture.quotes.presentation

import com.cotel.architecture.quotes.presentation.list.QuoteListFragment
import com.cotel.architecture.quotes.presentation.list.chucknorris.ChuckNorrisQuoteListFragment

class ChuckNorrisQuotesActivity : QuotesActivity() {
    override fun getQuoteListFragment(): QuoteListFragment =
        ChuckNorrisQuoteListFragment.newInstance()
}
