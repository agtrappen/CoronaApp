package com.example.coronaapp

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.coronaapp.databinding.FragmentInformationBindingImpl
import kotlinx.android.synthetic.main.fragment_information.*


class InformationFragment : Fragment(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var humidity: Sensor? = null
    private var temperature: Sensor? = null
    val currentLanguage: String = java.util.Locale.getDefault().getDisplayLanguage()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentInformationBindingImpl = DataBindingUtil.inflate(
            inflater, R.layout.fragment_information, container, false
        )

        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        humidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)
        temperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)

        binding.lifecycleOwner = this

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // Do something here if sensor accuracy changes.
    }

    
    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            temperature_value?.text = "${event.values[0]} °C"
        } else if (event.sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY) {
            humidity_value?.text = "${event.values[0]}  %"
            if (event.values[0] < 30) {
                if (currentLanguage.toLowerCase().contains("en")) {
                    corona_notification?.text = "The chance of transmitting corona based on the humidity of ${event.values[0]}% very small"
                } else {
                    corona_notification?.text = "De kans op overdragen van corona is vanwege luchtvochtigheid van ${event.values[0]}% heel klein"
                }
            } else if (event.values[0] >= 30 && event.values[0] <= 40) {
                if (currentLanguage.toLowerCase().contains("en")) {
                    corona_notification?.text = "The chance of transmitting corona based on the humidity of ${event.values[0]}% the biggest"
                } else {
                    corona_notification?.text = "De kans op overdragen van corona is vanwege luchtvochtigheid van ${event.values[0]}% het grootst"
                }
            } else {
                if (currentLanguage.toLowerCase().contains("en")) {
                    corona_notification?.text = "The chance of transmitting corona based on the humidity of ${event.values[0]}% moderate"
                } else {
                    corona_notification?.text = "De kans op overdragen van corona is vanwege luchtvochtigheid van ${event.values[0]}% matig"
                }
            }
        }
    }

    fun getLanguage(): String {
        return currentLanguage
    }

    fun getHumidity(): Sensor? {
        return humidity
    }

    override fun onResume() {
        // Register a listener for the sensor.
        super.onResume()
        sensorManager.registerListener(this, humidity, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, temperature, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        // Be sure to unregister the sensor when the activity pauses.
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