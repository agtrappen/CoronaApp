package com.example.coronaapp.network

import com.squareup.moshi.Json

data class CoronaProperty(
    val infected: Int,
    val recovered: Int,
    val deceased: Int
)