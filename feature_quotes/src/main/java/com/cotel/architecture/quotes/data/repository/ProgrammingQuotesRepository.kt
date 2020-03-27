package com.cotel.architecture.quotes.data.repository

import arrow.fx.IO
import arrow.fx.extensions.fx
import com.cotel.architecture.quotes.data.datasource.ProgrammingQuotesNetworkDataSource
import com.cotel.architecture.quotes.data.datasource.QuotesLocalDatasource
import com.cotel.architecture.quotes.domain.model.Quote
import com.cotel.architecture.quotes.domain.repository.QuotesRepository

class ProgrammingQuotesRepository(
    private val networkDataSource: ProgrammingQuotesNetworkDataSource,
    private val localDataSource: QuotesLocalDatasource
) : QuotesRepository {

    override fun getQuotes(page: Int): IO<List<Quote>> = IO.fx {
        val savedQuotes = !effect { localDataSource.getSavedQuotes() }
        val networkQuotes = !effect { networkDataSource.getQuotesByPage(page) }
        networkQuotes.map { networkQuote ->
            val savedQuote = savedQuotes.find { networkQuote.id == it.id }
            savedQuote ?: networkQuote
        }
    }

    override fun getSavedQuotes(): IO<List<Quote>> =
        IO.effect { localDataSource.getSavedQuotes() }

    override fun saveQuote(quote: Quote): IO<Unit> =
        IO.effect { localDataSource.saveQuote(quote) }

    override fun removeQuote(quote: Quote): IO<Unit> =
        IO.effect { localDataSource.removeQuote(quote) }
}
