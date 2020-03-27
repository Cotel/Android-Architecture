package com.cotel.architecture.quotes.data.datasource

import com.cotel.architecture.quotes.data.mapper.ChuckNorrisQuotesMapper
import com.cotel.architecture.quotes.data.network.ChuckNorrisQuotesService
import com.cotel.architecture.quotes.domain.model.Quote
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class ChuckNorrisQuotesNetworkDataSource(
    private val service: ChuckNorrisQuotesService
) : ChuckNorrisQuotesMapper {
    companion object {
        private const val PAGE_SIZE = 10
    }

    suspend fun getQuotesByPage(): List<Quote> =
        coroutineScope {
            (0 until PAGE_SIZE)
                .map { async { service.getRandomQuote() } }
                .map { it.await().toQuote() }
        }

}
