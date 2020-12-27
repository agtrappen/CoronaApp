package com.example.coronaapp.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.covid19api.com/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface CoronaApiService {
    @GET("summary")
    fun getProperties():
            Call<String>
}

object CoronaApi {
    val retrofitService : CoronaApiService by lazy {
        retrofit.create(CoronaApiService::class.java)
    }
}