package com.example.coronaapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coronaapp.network.CoronaApi
import com.example.coronaapp.network.CoronaProperty
import com.google.android.gms.common.util.ArrayUtils.contains
import kotlinx.coroutines.launch
import kotlinx.android.synthetic.main.fragment_title.textView2
import java.util.*

class TitleViewModel : ViewModel() {
    private val titleFragment = TitleFragment()

    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status

    private val _country = MutableLiveData<String>()
    val country: LiveData<String>
        get() = _country

    private val _property = MutableLiveData<CoronaProperty>()
    val property: LiveData<CoronaProperty>
        get() = _property

    private val _location = MutableLiveData<String>()
    val location: LiveData<String>
        get() = _location

    init {
        getCoronaProperties()
    }


    private fun getCoronaProperties() {
        viewModelScope.launch {
            try {
                Log.d("Test", "hhdsdfs")
                var listResult = CoronaApi.retrofitService.getProperties()
                if (listResult.size > 0) {
                    var i = 0
                    for (coronaProperty in listResult) {
                        if (coronaProperty.country == String.format(Locale("en"), TitleFragment.country.name)) {
                            _property.value = listResult[i]
                        }

                        i++
                    }
                }
            } catch (e: Exception) {
                Log.d("failure", "Failure: ${e.message}")
                _status.value = "Failure: ${e.message}"
            }
        }
    }
}