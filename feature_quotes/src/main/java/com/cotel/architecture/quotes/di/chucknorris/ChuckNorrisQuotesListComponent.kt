package com.cotel.architecture.quotes.di.chucknorris

import com.cotel.architecture.quotes.di.QuotesListScope
import com.cotel.architecture.quotes.presentation.list.QuoteListFragment
import dagger.Component

@QuotesListScope
@Component(modules = [ChuckNorrisQuotesModule::class])
interface ChuckNorrisQuotesListComponent {
    fun inject(quoteListFragment: QuoteListFragment)
}
