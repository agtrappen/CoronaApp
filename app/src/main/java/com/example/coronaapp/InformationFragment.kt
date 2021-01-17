package com.example.coronaapp

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.coronaapp.databinding.FragmentInformationBindingImpl
import kotlinx.android.synthetic.main.fragment_information.*

/**
 * Information fragment
 *
 * @constructor Create empty Information fragment
 */
class InformationFragment : Fragment(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var humidity: Sensor? = null
    private var temperature: Sensor? = null

    private val viewModel: InformationViewModel by lazy {
        ViewModelProvider(this).get(InformationViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentInformationBindingImpl = DataBindingUtil.inflate(
            inflater, R.layout.fragment_information, container, false
        )
        /**
         * Creates the information view
         * @param sensorManager lets us access and manage the device's sensors.
         * @param humidity defines the humidity device sensor.
         * @param temperature defines the temperature device sensor.
         */

        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        humidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)
        temperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)

        binding.lifecycleOwner = this

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onSensorChanged(event: SensorEvent) {
        /**
         * Creates the information about the humidity and tempratures
         * @param temperature_value defines the temperature and display's this in the view
         * @param humidity_value defines the humidity and display's this in the view
         * @param corona_notification defines the notification text based on the humidity and display's this in the view
         */
        if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            temperature_value?.text = "${event.values[0]} °C"
        } else if (event.sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY) {
            humidity_value?.text = "${event.values[0]}  %"
            if (event.values[0] < 30) {
                corona_notification?.text = "De kans op overdragen van corona is vanwege luchtvochtigheid van ${event.values[0]}% heel klein"
            } else if (event.values[0] >= 30 && event.values[0] <= 40) {
                corona_notification?.text = "De kans op overdragen van corona is vanwege luchtvochtigheid van ${event.values[0]}% het grootst"
            } else {
                corona_notification?.text = "De kans op overdragen van corona is vanwege luchtvochtigheid van ${event.values[0]}% matig"
            }
        }
    }

    override fun onResume() {
        /**
         * Register a listener for the sensor.
         */
        super.onResume()
        sensorManager.registerListener(this, humidity, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, temperature, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        /**
         * Unregister a listener for the sensor when the activity pauses.
         */
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item!!, requireView().findNavController()) || super.onOptionsItemSelected(item)
    }
}