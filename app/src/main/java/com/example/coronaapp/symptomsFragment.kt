package com.example.coronaapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.coronaapp.databinding.FragmentSymptomsBindingImpl
import com.example.coronaapp.database.CoronaDatabase
import com.example.coronaapp.database.CoronaSymptomsDao

class symptomsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("TEST", "HEY")

        val binding: FragmentSymptomsBindingImpl = DataBindingUtil.inflate(
                inflater, R.layout.fragment_symptoms, container, false
        )

        val application = requireNotNull(this.activity).application

        val dataSource = CoronaDatabase.getInstance(application).coronaSymptomsDao

        val viewModelFactory = SymptomsViewModelFactory(dataSource, application)

        val symptomsViewModel =
                ViewModelProvider(this, viewModelFactory).get(SymptomsViewModel::class.java)

        binding.symptomsViewModel = symptomsViewModel

        binding.setLifecycleOwner(this)

        return binding.root
    }
}