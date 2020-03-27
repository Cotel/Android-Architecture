package com.cotel.architecture.quotes.data.mapper

import com.cotel.architecture.quotes.data.dto.DbQuote
import com.cotel.architecture.quotes.domain.model.Quote

interface RoomQuotesMapper {
    fun DbQuote.toQuote(): Quote =
        Quote(
            id = id,
            text = text,
            author = author,
            isSaved = true
        )

    fun Quote.toDbQuote(): DbQuote =
        DbQuote(
            id = id,
            text = text,
            author = author
        )
}
