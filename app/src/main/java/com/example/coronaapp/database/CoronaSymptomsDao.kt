package com.example.coronaapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/**
 * Corona symptoms dao
 *
 * @constructor Create empty Corona symptoms dao
 */
@Dao
interface CoronaSymptomsDao {
    /**
     * Insert
     *
     * @param symptoms
     */
    @Insert
    fun insert(symptoms: CoronaSymptoms)

    /**
     * Get symptom based on ID
     *
     * @param key
     * @return
     */
    @Query("SELECT * from symptoms WHERE symptomId = :key")
    fun get(key: Long): CoronaSymptoms

    /**
     * Clear
     *
     */
    @Query("DELETE FROM symptoms")
    fun clear()

    /**
     * Get all symptoms
     *
     * @return
     */
    @Query("SELECT * FROM symptoms ORDER BY symptomId DESC")
    fun getAllSymptoms(): LiveData<List<CoronaSymptoms>>
}