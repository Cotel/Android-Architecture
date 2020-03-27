package com.cotel.architecture.quotes.data.mapper

import com.cotel.architecture.quotes.data.dto.ChuckNorrisQuote
import com.cotel.architecture.quotes.domain.model.Quote

interface ChuckNorrisQuotesMapper {
    fun ChuckNorrisQuote.toQuote(): Quote =
        Quote(
            id = id,
            text = value,
            author = "Chuck Norris"
        )
}
