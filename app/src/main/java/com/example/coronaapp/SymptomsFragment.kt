package com.example.coronaapp

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.coronaapp.databinding.FragmentSymptomsBindingImpl
import com.example.coronaapp.database.CoronaDatabase
import com.example.coronaapp.database.CoronaSymptomsDao
import kotlinx.android.synthetic.main.fragment_symptoms.*

class SymptomsFragment : Fragment() {
    private val viewModel: TitleViewModel by lazy {
        ViewModelProvider(this).get(TitleViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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

        setHasOptionsMenu(true)

        return binding.root
    }

    fun checkSymptoms() {
        Log.i("Test", "Testovich")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item!!, requireView().findNavController()) || super.onOptionsItemSelected(item)
    }
}