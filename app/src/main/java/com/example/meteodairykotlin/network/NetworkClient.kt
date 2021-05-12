package com.example.meteodairykotlin.network

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkClient @Inject constructor() {
    val logging: HttpLoggingInterceptor = HttpLoggingInterceptor()
    val transportClient: OkHttpClient = OkHttpClient.Builder().addInterceptor(logging).build()
    val apiClient: ApiClient = Retrofit.Builder()
        .client(transportClient)
        .baseUrl("https://www.gismeteo.ru/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(ApiClient::class.java)
}