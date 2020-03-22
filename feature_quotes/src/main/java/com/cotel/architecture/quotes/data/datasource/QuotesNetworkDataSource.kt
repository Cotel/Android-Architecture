package com.cotel.architecture.quotes.data.datasource

import arrow.core.Either
import com.cotel.architecture.quotes.data.mapper.ProgrammingQuotesMapper
import com.cotel.architecture.quotes.data.network.ProgrammingQuotesService
import com.cotel.architecture.quotes.domain.model.Quote

class QuotesNetworkDataSource(
    private val service: ProgrammingQuotesService
) : ProgrammingQuotesMapper {

    suspend fun getQuotesByPage(page: Int): Either<Throwable, List<Quote>> =
        Either.catch {
            service.getQuotesByPage(page)
                .map { apiQuote -> apiQuote.toQuote() }
        }
}
