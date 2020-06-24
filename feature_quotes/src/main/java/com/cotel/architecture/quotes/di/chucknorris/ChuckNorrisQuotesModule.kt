package com.cotel.architecture.quotes.di.chucknorris

import com.cotel.architecture.quotes.data.network.ChuckNorrisQuotesService
import com.cotel.architecture.quotes.data.repository.ChuckNorrisQuotesRepository
import com.cotel.architecture.quotes.di.QuotesListScope
import com.cotel.architecture.quotes.domain.repository.QuotesRepository
import com.cotel.architecture.quotes.presentation.list.QuoteListRenderer
import com.cotel.architecture.quotes.presentation.list.chucknorris.ChuckNorrisQuoteListRenderer
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [ChuckNorrisQuotesBindings::class])
class ChuckNorrisQuotesModule {
    @QuotesListScope
    @Provides
    fun providesApiService(): ChuckNorrisQuotesService =
        ChuckNorrisQuotesService.create()
}

@Module
abstract class ChuckNorrisQuotesBindings {
    @Binds
    abstract fun bindsRepository(
        repository: ChuckNorrisQuotesRepository
    ): QuotesRepository

    @Binds
    abstract fun bindRenderer(
        renderer: ChuckNorrisQuoteListRenderer
    ): QuoteListRenderer
}
