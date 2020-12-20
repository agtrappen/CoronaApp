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

    @Query("SELECT * from symptoms WHERE symptomId = :key")
    fun get(key: Long): CoronaSymptoms

    @Query("DELETE FROM symptoms")
    fun clear()

    @Query("SELECT * FROM symptoms ORDER BY symptomId DESC")
    fun getAllSymptoms(): LiveData<List<CoronaSymptoms>>
}