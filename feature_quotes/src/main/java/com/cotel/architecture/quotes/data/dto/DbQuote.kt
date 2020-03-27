package com.cotel.architecture.quotes.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quote")
data class DbQuote(
    @PrimaryKey val id: String,
    val text: String,
    val author: String
)
