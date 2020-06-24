package com.cotel.architecture.quotes.presentation

import com.cotel.architecture.quotes.presentation.list.QuoteListFragment
import com.cotel.architecture.quotes.presentation.list.programming.ProgrammingQuoteListFragment

class ProgrammingQuotesActivity : QuotesActivity() {
    override fun getQuoteListFragment(): QuoteListFragment =
        ProgrammingQuoteListFragment.newInstance()
}