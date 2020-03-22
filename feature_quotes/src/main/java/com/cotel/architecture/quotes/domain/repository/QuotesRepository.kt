package com.cotel.architecture.quotes.domain.repository

import arrow.core.Either
import com.cotel.architecture.quotes.domain.model.Quote

interface QuotesRepository {
    suspend fun getQuotes(page: Int): Either<Throwable, List<Quote>>
}
