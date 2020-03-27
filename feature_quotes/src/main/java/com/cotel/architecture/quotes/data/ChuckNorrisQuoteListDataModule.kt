package com.cotel.architecture.quotes.data

import com.cotel.architecture.quotes.data.datasource.ChuckNorrisQuotesNetworkDataSource
import com.cotel.architecture.quotes.data.network.ChuckNorrisQuotesService
import com.cotel.architecture.quotes.data.repository.ChuckNorrisQuotesRepository
import com.cotel.architecture.quotes.domain.repository.QuotesRepository
import org.koin.dsl.module

val chuckNorrisQuoteListDataModule = module {
    single { ChuckNorrisQuotesService.create() }

    single { ChuckNorrisQuotesNetworkDataSource(service = get()) }

    single<QuotesRepository> {
        ChuckNorrisQuotesRepository(networkDataSource = get())
    }
}
