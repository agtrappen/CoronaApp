package com.example.coronaapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.coronaapp.database.CoronaSymptomsDao
import kotlinx.coroutines.Job
import java.util.*

/**
 * Symptoms view model
 *
 * @property database
 * @constructor
 *
 * @param application
 */
class SymptomsViewModel(
        val database: CoronaSymptomsDao,
        application: Application) : AndroidViewModel(application) {
        val currentLanguage: String = Locale.getDefault().getDisplayLanguage()

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

        fun checkLanguage(string: String): String {
                if (string == symptomsString1) {
                        if (currentLanguage.toLowerCase().contains("en")) {
                                return "fever"
                        }
                }

                if (string == symptomsString2) {
                        if (currentLanguage.toLowerCase().contains("en")) {
                                return "dry cough"
                        }
                }

                if (string == symptomsString3) {
                        if (currentLanguage.toLowerCase().contains("en")) {
                                return "fatigue"
                        }
                }

                if (string == symptomsString4) {
                        if (currentLanguage.toLowerCase().contains("en")) {
                                return "pain in body"
                        }
                }

                return string
        }
}