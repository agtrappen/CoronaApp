package com.example.coronaapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coronaapp.network.CoronaApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.await

class TitleViewModel : ViewModel() {
    private val _infected = MutableLiveData<String>()
    val infected: LiveData<String>
        get() = _infected


    private val _deceased = MutableLiveData<String>()
    val deceased: LiveData<String>
        get() = _deceased


    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    init {
        getCoronaProperties()
    }

    private fun getCoronaProperties() {
        coroutineScope.launch {
            var getPropertiesDeferred = CoronaApi.retrofitService.getProperties()
            try {
                var listResult = getPropertiesDeferred.await()
                _infected.value = "${listResult.infected}"
                _deceased.value = "${listResult.deceased}"
            } catch (e: Exception) {
                _infected.value = "Failure: ${e.message}"
                _deceased.value = "Failure: ${e.message}"
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}