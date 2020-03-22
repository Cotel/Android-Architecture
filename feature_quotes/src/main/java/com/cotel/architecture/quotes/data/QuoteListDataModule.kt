package com.cotel.architecture.quotes.data

import com.cotel.architecture.quotes.data.datasource.QuotesNetworkDataSource
import com.cotel.architecture.quotes.data.network.ProgrammingQuotesService
import com.cotel.architecture.quotes.data.repository.DefaultQuotesRepository
import com.cotel.architecture.quotes.domain.repository.QuotesRepository
import org.koin.dsl.module

val quoteListDataModule = module {

    single { ProgrammingQuotesService.create() }

    single { QuotesNetworkDataSource(service = get()) }

    single<QuotesRepository> { DefaultQuotesRepository(networkDataSource = get()) }
}
