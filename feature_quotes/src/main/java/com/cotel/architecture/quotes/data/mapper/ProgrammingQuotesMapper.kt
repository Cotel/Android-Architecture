package com.cotel.architecture.quotes.data.mapper

import com.cotel.architecture.quotes.data.dto.ApiQuote
import com.cotel.architecture.quotes.data.dto.DbQuote
import com.cotel.architecture.quotes.domain.model.Quote

interface ProgrammingQuotesMapper {
    fun ApiQuote.toQuote(): Quote =
        Quote(
            id = _id,
            text = en,
            author = author,
            isSaved = false
        )
}
