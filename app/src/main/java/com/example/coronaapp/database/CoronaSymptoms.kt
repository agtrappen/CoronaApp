package com.example.coronaapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Corona symptoms
 *
 * @property symptomId Identifier for a symptom
 * @property symptom Description of the symptom
 * @constructor Create empty Corona symptoms
 */
@Entity(tableName = "symptoms")
data class CoronaSymptoms(
        @PrimaryKey(autoGenerate = true)
        var symptomId: Long = 0L,

        @ColumnInfo(name = "corona_symptom")
        var symptom: String
)