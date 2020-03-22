package com.cotel.architecture.quotes.data.repository

import arrow.core.Either
import com.cotel.architecture.quotes.data.datasource.QuotesNetworkDataSource
import com.cotel.architecture.quotes.domain.model.Quote
import com.cotel.architecture.quotes.domain.repository.QuotesRepository

class DefaultQuotesRepository(
    private val networkDataSource: QuotesNetworkDataSource
) : QuotesRepository {
    override suspend fun getQuotes(page: Int): Either<Throwable, List<Quote>> =
        networkDataSource.getQuotesByPage(page)
}
