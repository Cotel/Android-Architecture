package com.cotel.architecture.quotes.data

import com.cotel.architecture.quotes.data.datasource.QuotesLocalDatasource
import com.cotel.architecture.quotes.data.datasource.ProgrammingQuotesNetworkDataSource
import com.cotel.architecture.quotes.data.local.QuoteDatabase
import com.cotel.architecture.quotes.data.network.ProgrammingQuotesService
import com.cotel.architecture.quotes.data.repository.ProgrammingQuotesRepository
import com.cotel.architecture.quotes.domain.repository.QuotesRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val programmingQuoteListDataModule = module {
    single { QuoteDatabase(androidApplication()) }

    single { get<QuoteDatabase>().quoteDao() }

    single { ProgrammingQuotesService.create() }

    single { QuotesLocalDatasource(dao = get()) }

    single { ProgrammingQuotesNetworkDataSource(service = get()) }

    single<QuotesRepository> {
        ProgrammingQuotesRepository(
            localDataSource = get(),
            networkDataSource = get()
        )
    }
}
