package com.example.coronaapp

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.coronaapp.database.CoronaDatabase
import com.example.coronaapp.databinding.FragmentSymptomsBindingImpl
import kotlinx.android.synthetic.main.fragment_symptoms.*
import java.util.*

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

    private fun showDialog() {
        val currentLanguage: String = Locale.getDefault().getDisplayLanguage()

        val dialog = MaterialDialog(requireContext())
                .noAutoDismiss()
                .customView(R.layout.result_dialog)

        dialog.findViewById<TextView>(R.id.cancel_button).setOnClickListener {
            dialog.dismiss()
        }

        var count = 0
        if (checkBox.isChecked()) {
            count++
        }

        if (checkBox2.isChecked()) {
            count++
        }

        if (checkBox3.isChecked()) {
            count++
        }

        if (checkBox4.isChecked()) {
            count++
        }

        if (count > 2) {
            if (currentLanguage.toLowerCase().contains("en")) {
                dialog.findViewById<TextView>(R.id.advice_text).text = "The advice based on your symptoms is that you should be tested for corona. You can do this on the website of the national government by logging in with your digiD or by calling: 0800-1202"
            } else {
                dialog.findViewById<TextView>(R.id.advice_text).text = "Het advies op basis van uw ingevulde symptomen luidt dat u zich moeten laten testen op corona. U kunt dit doen op de website van de rijksoverheid door in te loggen met uw digiD of telefonisch contact op te nemen met:0800-1202"
            }
        } else {
            if (currentLanguage.toLowerCase().contains("en")) {
                dialog.findViewById<TextView>(R.id.advice_text).text = "The advice based on your completed symptoms is that you do not need to be tested for corona."
            } else {
                dialog.findViewById<TextView>(R.id.advice_text).text = "Het advies op basis van uw ingevulde symptomen luidt dat u zich niet hoeft te laten testen op corona."
            }
        }


        dialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.overflow_menu, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        advice_button.setOnClickListener {
            showDialog()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item!!, requireView().findNavController()) || super.onOptionsItemSelected(item)
    }
}