package com.cotel.architecture.quotes.data.repository

import arrow.fx.IO
import arrow.fx.extensions.fx
import com.cotel.architecture.quotes.data.datasource.QuotesLocalDatasource
import com.cotel.architecture.quotes.data.datasource.QuotesNetworkDataSource
import com.cotel.architecture.quotes.domain.model.Quote
import com.cotel.architecture.quotes.domain.repository.QuotesRepository

class DefaultQuotesRepository(
    private val networkDataSource: QuotesNetworkDataSource,
    private val localDataSource: QuotesLocalDatasource
) : QuotesRepository {

    override fun getQuotes(page: Int): IO<List<Quote>> = IO.fx {
        val networkQuotes = !effect { networkDataSource.getQuotesByPage(page) }
        !networkQuotes
            .parTraverse { quote -> effect { checkIfQuoteIsSaved(quote) } }
    }

    override fun saveQuote(quote: Quote): IO<Unit> =
        IO.effect { localDataSource.saveQuote(quote) }

    private suspend fun checkIfQuoteIsSaved(quote: Quote): Quote =
        localDataSource.findSavedQuoteById(quote.id) ?: quote


}
