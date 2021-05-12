package com.example.meteodairykotlin.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiClient {
    @GET("diary/{city}/{year}/{month}")
    fun loadDiary(@Path("year") year:Int,
                  @Path("month") month:Int,
                  @Path("city") city:Int):Single<String>

    @GET("weather{nameCity}{city}/10-days/")
    fun loadWeather(@Path("nameCity") nameCity:String,
                  @Path("city") city:Int):Single<String>
}