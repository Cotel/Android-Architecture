package com.cotel.architecture.quotes.data

import com.cotel.architecture.quotes.data.datasource.QuotesLocalDatasource
import com.cotel.architecture.quotes.data.datasource.QuotesNetworkDataSource
import com.cotel.architecture.quotes.data.local.QuoteDatabase
import com.cotel.architecture.quotes.data.network.ProgrammingQuotesService
import com.cotel.architecture.quotes.data.repository.DefaultQuotesRepository
import com.cotel.architecture.quotes.domain.repository.QuotesRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val quoteListDataModule = module {
    single { QuoteDatabase(androidApplication()) }

    single { get<QuoteDatabase>().quoteDao() }

    single { ProgrammingQuotesService.create() }

    single { QuotesLocalDatasource(dao = get()) }

    single { QuotesNetworkDataSource(service = get()) }

    single<QuotesRepository> {
        DefaultQuotesRepository(
            localDataSource = get(),
            networkDataSource = get()
        )
    }
}
