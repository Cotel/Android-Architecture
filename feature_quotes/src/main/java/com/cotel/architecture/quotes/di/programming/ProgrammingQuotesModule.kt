package com.cotel.architecture.quotes.di.programming

import android.app.Application
import android.content.Context
import com.cotel.architecture.quotes.data.datasource.ProgrammingQuotesNetworkDataSource
import com.cotel.architecture.quotes.data.datasource.QuotesLocalDatasource
import com.cotel.architecture.quotes.data.local.QuoteDatabase
import com.cotel.architecture.quotes.data.network.ProgrammingQuotesService
import com.cotel.architecture.quotes.data.repository.ProgrammingQuotesRepository
import com.cotel.architecture.quotes.di.QuotesListScope
import com.cotel.architecture.quotes.domain.repository.QuotesRepository
import com.cotel.architecture.quotes.presentation.list.QuoteListRenderer
import com.cotel.architecture.quotes.presentation.list.QuoteListViewModel
import com.cotel.architecture.quotes.presentation.list.programming.ProgrammingQuoteListRenderer
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ProgrammingQuotesModule {

    @Provides
    @QuotesListScope
    fun providesRenderer(): QuoteListRenderer =
        ProgrammingQuoteListRenderer()

    @Provides
    @QuotesListScope
    fun providesViewModel(repository: QuotesRepository): QuoteListViewModel =
        QuoteListViewModel(repository)

    @Provides
    @QuotesListScope
    fun providesRepository(
        networkDataSource: ProgrammingQuotesNetworkDataSource,
        localDatasource: QuotesLocalDatasource
    ): QuotesRepository =
        ProgrammingQuotesRepository(networkDataSource, localDatasource)

    @Provides
    @QuotesListScope
    fun providesNetworkDatasource(
        service: ProgrammingQuotesService
    ): ProgrammingQuotesNetworkDataSource =
        ProgrammingQuotesNetworkDataSource(service)

    @Provides
    @QuotesListScope
    fun providesDatabase(appContext: Context): QuoteDatabase =
        QuoteDatabase(appContext)

    @Provides
    @QuotesListScope
    fun providesLocalDatasource(
        database: QuoteDatabase
    ): QuotesLocalDatasource =
        QuotesLocalDatasource(database.quoteDao())

    @Provides
    @QuotesListScope
    fun providesApiService(): ProgrammingQuotesService =
        ProgrammingQuotesService.create()
}
