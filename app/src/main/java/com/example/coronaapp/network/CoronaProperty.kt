package com.example.coronaapp.network

import com.squareup.moshi.Json

data class CoronaProperty(
    val infected: String,
    val recovered: String,
    val deceased: String,
    val moreData: String,
    val country: String
)