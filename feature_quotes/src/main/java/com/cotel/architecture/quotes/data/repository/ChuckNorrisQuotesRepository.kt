package com.cotel.architecture.quotes.data.repository

import arrow.fx.IO
import com.cotel.architecture.quotes.data.datasource.ChuckNorrisQuotesNetworkDataSource
import com.cotel.architecture.quotes.di.QuotesListScope
import com.cotel.architecture.quotes.domain.model.Quote
import com.cotel.architecture.quotes.domain.repository.QuotesRepository
import javax.inject.Inject

@QuotesListScope
class ChuckNorrisQuotesRepository @Inject constructor(
    private val networkDataSource: ChuckNorrisQuotesNetworkDataSource
) : QuotesRepository {

    override fun getQuotes(page: Int): IO<List<Quote>> =
        IO.effect { networkDataSource.getQuotesByPage() }

    override fun getSavedQuotes(): IO<List<Quote>> =
        IO.just(emptyList())

    override fun saveQuote(quote: Quote): IO<Unit> =
        IO.just(Unit)

    override fun removeQuote(quote: Quote): IO<Unit> =
        IO.just(Unit)
}
