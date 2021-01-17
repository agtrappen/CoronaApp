package com.example.coronaapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Transformations
import com.example.coronaapp.database.CoronaSymptoms
import com.example.coronaapp.database.CoronaSymptomsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext

/**
 * Symptoms view model
 *
 * @property database
 * @constructor
 *
 * @param application
 */
class SymptomsViewModel (
        val database: CoronaSymptomsDao,
        application: Application) : AndroidViewModel(application) {

        private var viewModelJob = Job()

        override fun onCleared() {
                super.onCleared()
                viewModelJob.cancel()
        }

        private val symptom1 = database.get(1)
        val symptomsString1 = symptom1.symptom

        private val symptom2 = database.get(2)
        val symptomsString2 = symptom2.symptom

        private val symptom3 = database.get(3)
        val symptomsString3 = symptom3.symptom

        private val symptom4 = database.get(4)
        val symptomsString4 = symptom4.symptom
}