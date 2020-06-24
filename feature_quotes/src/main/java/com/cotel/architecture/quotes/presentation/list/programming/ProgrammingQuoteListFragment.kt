package com.cotel.architecture.quotes.presentation.list.programming

import android.content.Context
import com.cotel.architecture.ApplicationComponentProvider
import com.cotel.architecture.quotes.di.programming.DaggerProgrammingQuotesListComponent
import com.cotel.architecture.quotes.di.programming.ProgrammingQuotesModule
import com.cotel.architecture.quotes.presentation.list.QuoteListFragment

class ProgrammingQuoteListFragment : QuoteListFragment() {
    companion object {
        fun newInstance(): QuoteListFragment = ProgrammingQuoteListFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val applicationComponent = (requireActivity().application
            as ApplicationComponentProvider)
            .getApplicationComponent()

        DaggerProgrammingQuotesListComponent.builder()
            .applicationComponent(applicationComponent)
            .programmingQuotesModule(ProgrammingQuotesModule())
            .build()
            .inject(this)
    }
}
