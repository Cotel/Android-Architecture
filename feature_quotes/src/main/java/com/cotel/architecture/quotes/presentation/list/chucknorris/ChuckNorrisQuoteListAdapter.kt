package com.cotel.architecture.quotes.presentation.list.chucknorris

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cotel.architecture.quotes.R
import com.cotel.architecture.quotes.domain.model.Quote
import com.cotel.architecture.quotes.presentation.list.QuoteItemDiff
import kotlinx.android.synthetic.main.item_chucknorris_quote.view.*

class ChuckNorrisQuoteListAdapter :
    ListAdapter<Quote, ChuckNorrisQuoteListAdapter.ViewHolder>(QuoteItemDiff) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chucknorris_quote, parent, false)
            .let { ViewHolder(it) }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(quote: Quote) {
            with (itemView) {
                quote_author.text = quote.author
                quote_text.text = quote.text
            }
        }
    }

}
