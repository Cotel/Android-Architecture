package com.cotel.architecture.quotes.di.programming

import com.cotel.architecture.ApplicationComponent
import com.cotel.architecture.quotes.di.QuotesListScope
import com.cotel.architecture.quotes.presentation.list.QuoteListFragment
import dagger.Component

@QuotesListScope
@Component(
    modules = [ProgrammingQuotesModule::class],
    dependencies = [ApplicationComponent::class]
)
interface ProgrammingQuotesListComponent {
    fun inject(quoteListFragment: QuoteListFragment)
}
