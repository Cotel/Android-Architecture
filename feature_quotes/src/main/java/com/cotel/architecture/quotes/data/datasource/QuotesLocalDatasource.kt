package com.cotel.architecture.quotes.data.datasource

import com.cotel.architecture.quotes.data.local.QuoteDao
import com.cotel.architecture.quotes.data.mapper.RoomQuotesMapper
import com.cotel.architecture.quotes.domain.model.Quote

class QuotesLocalDatasource(
    private val dao: QuoteDao
) : RoomQuotesMapper {
    suspend fun getSavedQuotes(): List<Quote> =
        dao.queryAll().map { it.toQuote() }

    suspend fun findSavedQuoteById(id: String): Quote? =
        dao.queryById(id)?.toQuote()

    suspend fun saveQuote(quote: Quote): Unit =
        dao.insert(quote.toDbQuote())

    suspend fun removeQuote(quote: Quote): Unit =
        dao.remove(quote.toDbQuote())
}
