package com.example.coronaapp.network

import androidx.annotation.Nullable
import com.squareup.moshi.Json
import java.text.DecimalFormat
import java.text.NumberFormat

data class CoronaProperty(
    val infected: Int?,
    val recovered: String?,
    val deceased: Int?,
    val moreData: String?,
    val country: String?
)