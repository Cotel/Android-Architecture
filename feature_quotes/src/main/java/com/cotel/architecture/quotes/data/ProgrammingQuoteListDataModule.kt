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
    single(override = true) { QuoteDatabase(androidApplication()) }

    single(override = true) { get<QuoteDatabase>().quoteDao() }

    single(override = true) { ProgrammingQuotesService.create() }

    single(override = true) { QuotesLocalDatasource(dao = get()) }

    single(override = true) { ProgrammingQuotesNetworkDataSource(service = get()) }

    single<QuotesRepository>(override = true) {
        ProgrammingQuotesRepository(
            localDataSource = get(),
            networkDataSource = get()
        )
    }
}
