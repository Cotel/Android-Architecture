package com.cotel.architecture.quotes.domain.repository

import arrow.fx.IO
import com.cotel.architecture.quotes.domain.model.Quote

interface QuotesRepository {
    fun getQuotes(page: Int): IO<List<Quote>>
    fun saveQuote(quote: Quote): IO<Unit>
}
