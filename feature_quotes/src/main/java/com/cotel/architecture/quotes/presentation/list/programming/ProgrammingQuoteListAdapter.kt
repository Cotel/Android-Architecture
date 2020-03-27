package com.cotel.architecture.quotes.presentation.list.programming

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cotel.architecture.base.presentation.extension.gone
import com.cotel.architecture.base.presentation.extension.visible
import com.cotel.architecture.quotes.R
import com.cotel.architecture.quotes.domain.model.Quote
import com.cotel.architecture.quotes.presentation.list.QuoteItemDiff
import kotlinx.android.synthetic.main.item_programming_quote.view.*

internal class ProgrammingQuoteListAdapter(
    private val onQuoteClick: (Quote) -> Unit
) : ListAdapter<Quote, ProgrammingQuoteListAdapter.ViewHolder>(QuoteItemDiff) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_programming_quote, parent, false)
        return ViewHolder(
            layout,
            onQuoteClick
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    internal class ViewHolder(
        itemView: View,
        private val onQuoteClick: (Quote) -> Unit
    ) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(quote: Quote) {
            with(itemView) {
                quote_text.text = quote.text
                quote_author.text = quote.author

                if (quote.isSaved)
                    quote_is_saved.visible()
                else
                    quote_is_saved.gone()

                setOnClickListener { onQuoteClick(quote) }
            }
        }
    }
}
