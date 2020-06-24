package com.cotel.architecture.quotes.di.chucknorris

import com.cotel.architecture.quotes.data.datasource.ChuckNorrisQuotesNetworkDataSource
import com.cotel.architecture.quotes.data.network.ChuckNorrisQuotesService
import com.cotel.architecture.quotes.data.repository.ChuckNorrisQuotesRepository
import com.cotel.architecture.quotes.di.QuotesListScope
import com.cotel.architecture.quotes.domain.repository.QuotesRepository
import com.cotel.architecture.quotes.presentation.list.QuoteListRenderer
import com.cotel.architecture.quotes.presentation.list.QuoteListViewModel
import com.cotel.architecture.quotes.presentation.list.chucknorris.ChuckNorrisQuoteListRenderer
import dagger.Module
import dagger.Provides

@Module
class ChuckNorrisQuotesModule {

    @QuotesListScope
    @Provides
    fun providesRenderer(): QuoteListRenderer =
        ChuckNorrisQuoteListRenderer()

    @QuotesListScope
    @Provides
    fun providesViewModel(repository: QuotesRepository): QuoteListViewModel =
        QuoteListViewModel(repository)

    @QuotesListScope
    @Provides
    fun providesRepository(
        networkDataSource: ChuckNorrisQuotesNetworkDataSource
    ): QuotesRepository =
        ChuckNorrisQuotesRepository(networkDataSource)

    @QuotesListScope
    @Provides
    fun providesNetworkDatasource(
        service: ChuckNorrisQuotesService
    ): ChuckNorrisQuotesNetworkDataSource =
        ChuckNorrisQuotesNetworkDataSource(service)

    @QuotesListScope
    @Provides
    fun providesApiService(): ChuckNorrisQuotesService =
        ChuckNorrisQuotesService.create()
}
