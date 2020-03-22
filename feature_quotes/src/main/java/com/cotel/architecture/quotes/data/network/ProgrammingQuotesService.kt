package com.cotel.architecture.quotes.data.network

import com.cotel.architecture.network.RetrofitServiceFactory
import com.cotel.architecture.quotes.data.dto.ApiQuote
import retrofit2.http.GET
import retrofit2.http.Path

interface ProgrammingQuotesService {
    @GET("quotes/page/{page}")
    suspend fun getQuotesByPage(
        @Path("page") page: Int
    ): List<ApiQuote>

    companion object Factory : RetrofitServiceFactory() {
        override val baseUrl: String =
            "https://programming-quotes-api.herokuapp.com/"

        fun create(): ProgrammingQuotesService =
            create(ProgrammingQuotesService::class.java)
    }
}
