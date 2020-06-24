package com.cotel.architecture.quotes.di.programming

import android.content.Context
import com.cotel.architecture.quotes.data.local.QuoteDao
import com.cotel.architecture.quotes.data.local.QuoteDatabase
import com.cotel.architecture.quotes.data.network.ProgrammingQuotesService
import com.cotel.architecture.quotes.data.repository.ProgrammingQuotesRepository
import com.cotel.architecture.quotes.di.QuotesListScope
import com.cotel.architecture.quotes.domain.repository.QuotesRepository
import com.cotel.architecture.quotes.presentation.list.QuoteListRenderer
import com.cotel.architecture.quotes.presentation.list.programming.ProgrammingQuoteListRenderer
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [ProgrammingQuotesBindings::class])
class ProgrammingQuotesModule {
    @Provides
    @QuotesListScope
    fun providesDatabase(appContext: Context): QuoteDatabase =
        QuoteDatabase(appContext)

    @Provides
    @QuotesListScope
    fun providesQuotesDao(database: QuoteDatabase): QuoteDao =
        database.quoteDao()

    @Provides
    @QuotesListScope
    fun providesApiService(): ProgrammingQuotesService =
        ProgrammingQuotesService.create()
}

@Module
abstract class ProgrammingQuotesBindings {
    @Binds
    abstract fun bindsRepository(
        repository: ProgrammingQuotesRepository
    ): QuotesRepository

    @Binds
    abstract fun bindsRenderer(
        renderer: ProgrammingQuoteListRenderer
    ): QuoteListRenderer
}
