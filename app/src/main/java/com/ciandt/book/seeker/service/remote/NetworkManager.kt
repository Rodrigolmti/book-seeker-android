package com.ciandt.book.seeker.service.remote

import com.ciandt.book.seeker.util.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL ="https://itunes.apple.com/"

fun provideDefaultOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

fun provideRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
}

fun provideBookWebService(retrofit: Retrofit): BookWebService =
    retrofit.create(BookWebService::class.java)