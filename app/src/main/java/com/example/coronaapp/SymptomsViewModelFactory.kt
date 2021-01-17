package com.example.coronaapp

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coronaapp.database.CoronaSymptomsDao

/**
 * Symptoms view model factory
 *
 * @property dataSource defenise the Database
 * @property application
 * @constructor Create empty Symptoms view model factory
 */
class SymptomsViewModelFactory(
        private val dataSource: CoronaSymptomsDao,
        private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SymptomsViewModel::class.java)) {
            return SymptomsViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}