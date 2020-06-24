package com.cotel.architecture.quotes.data.datasource

import com.cotel.architecture.quotes.data.mapper.ProgrammingQuotesMapper
import com.cotel.architecture.quotes.data.network.ProgrammingQuotesService
import com.cotel.architecture.quotes.di.QuotesListScope
import com.cotel.architecture.quotes.domain.model.Quote
import javax.inject.Inject

@QuotesListScope
class ProgrammingQuotesNetworkDataSource @Inject constructor(
    private val service: ProgrammingQuotesService
) : ProgrammingQuotesMapper {

    suspend fun getQuotesByPage(page: Int): List<Quote> =
        service.getQuotesByPage(page)
            .map { apiQuote -> apiQuote.toQuote() }
}
