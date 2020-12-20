package com.example.coronaapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "symptoms")
data class CoronaSymptoms(
        @PrimaryKey(autoGenerate = true)
        var symptomId: Long = 0L,

        @ColumnInfo(name = "corona_symptom")
        var symptom: String
)