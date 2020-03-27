package com.cotel.architecture.quotes.domain.model

data class Quote(
    val id: String,
    val text: String,
    val author: String,
    val isSaved: Boolean = false
)
