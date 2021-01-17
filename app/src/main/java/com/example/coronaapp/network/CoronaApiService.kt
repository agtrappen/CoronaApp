package com.example.coronaapp.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.apify.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface CoronaApiService {
    @GET("v2/key-value-stores/tVaYRsPHLjNdNBu7S/records/LATEST?disableRedirect=true?")
    suspend fun getProperties():
            List<CoronaProperty>
}

object CoronaApi {
    val retrofitService : CoronaApiService by lazy {
        retrofit.create(CoronaApiService::class.java)
    }
}