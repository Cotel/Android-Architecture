package com.cotel.architecture.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

abstract class RetrofitServiceFactory {
    protected abstract val baseUrl: String

    protected fun <S> create(service: Class<S>): S =
        retrofit().create(service)

    private fun retrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient())
            .build()

    private fun okHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor())
            .build()

    private fun httpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
}
