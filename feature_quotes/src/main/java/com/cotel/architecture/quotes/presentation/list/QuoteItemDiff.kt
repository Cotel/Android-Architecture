package com.cotel.architecture.quotes.presentation.list

import androidx.recyclerview.widget.DiffUtil
import com.cotel.architecture.quotes.domain.model.Quote

object QuoteItemDiff : DiffUtil.ItemCallback<Quote>() {
    override fun areItemsTheSame(oldItem: Quote, newItem: Quote): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Quote, newItem: Quote): Boolean =
        oldItem == newItem
}
