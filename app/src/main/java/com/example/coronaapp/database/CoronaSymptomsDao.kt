package com.example.coronaapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CoronaSymptomsDao {
    @Insert
    fun insert(symptoms: CoronaSymptoms)

    @Query("SELECT * from corona_symptoms WHERE symptomId = :key")
    fun get(key: Long): CoronaSymptoms

    @Query("DELETE FROM corona_symptoms")
    fun clear()

    @Query("SELECT * FROM corona_symptoms ORDER BY symptomId DESC")
    fun getAllSymptoms(): LiveData<List<CoronaSymptoms>>
}