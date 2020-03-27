package com.cotel.architecture.quotes.data.network

import com.cotel.architecture.network.RetrofitServiceFactory
import com.cotel.architecture.quotes.data.dto.ChuckNorrisQuote
import retrofit2.http.GET

interface ChuckNorrisQuotesService {
    @GET("jokes/random")
    suspend fun getRandomQuote(): ChuckNorrisQuote

    companion object Factory : RetrofitServiceFactory() {
        override val baseUrl: String =
            "https://api.chucknorris.io/"

        fun create(): ChuckNorrisQuotesService =
            create(ChuckNorrisQuotesService::class.java)
    }
}
