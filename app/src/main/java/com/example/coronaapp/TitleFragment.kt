package com.example.coronaapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.coronaapp.databinding.FragmentTitleBindingImpl
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.fragment_title.*
import java.util.*

/**
 * Title fragment
 *
 * @constructor Create empty Title fragment
 */
class TitleFragment : Fragment(), LocationListener {
    private val viewModel: TitleViewModel by lazy {
        ViewModelProvider(this).get(TitleViewModel::class.java)
    }

    //Declaring the needed Variables
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    val PERMISSION_ID = 1010

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentTitleBindingImpl = DataBindingUtil.inflate(
            inflater, R.layout.fragment_title,  container, false
        )

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        RequestPermission()

        getLastLocation()

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        return binding.root
    }

    /**
     * Get last device location
     *
     */
    fun getLastLocation(){
        if(CheckPermission()){
            if(isLocationEnabled()){
                if (ActivityCompat.checkSelfPermission(
                                requireActivity(),
                                Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                requireActivity(),
                                Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task->
                    var location: Location? = task.result
                    if(location == null){
                        NewLocationData()
                    }else{
                        Log.d("Debug:" ,"Your Location:"+ location.longitude)
                        getCityName(location.latitude,location.longitude)
                    }
                }
            }else{
                Toast.makeText(requireActivity(),"Please Turn on Your device Location", Toast.LENGTH_SHORT).show()
            }
        }else{
            RequestPermission()
        }
    }


    /**
     * New location data
     *
     */
    fun NewLocationData(){
        var locationRequest =  LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        if (ActivityCompat.checkSelfPermission(
                        requireActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        requireActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationProviderClient!!.requestLocationUpdates(
                locationRequest,locationCallback, Looper.myLooper()
        )
    }


    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult) {
            var lastLocation: Location = locationResult.lastLocation
            Log.d("Debug:","your last last location: "+ lastLocation.longitude.toString())
            textView.text = getCityName(lastLocation.latitude,lastLocation.longitude)
        }
    }
    /**
     * Checks if we have permission
     * @return true if we have
     * @return false if not
     */
    private fun CheckPermission():Boolean{
        if(
                ActivityCompat.checkSelfPermission(requireActivity(),android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(requireActivity(),android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ){
            return true
        }

        return false

    }

    /**
     * Request permission this function will allows us to tell the user to request the necessary permision if they are not garented
     *
     */
    fun RequestPermission(){

        ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_ID
        )
    }

    /**
     * Is location enabled this function will return to us the state of the location service
     * @return true if the gps or the network provider is enabled
     * @return false if not
     */
    fun isLocationEnabled():Boolean{
        var locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }


    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        if(requestCode == PERMISSION_ID){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("Debug:","You have the Permission")
            }
        }
    }

    /**
     * Function to create a city + country name based on a geocode and displays this in the view
     * @return city + country name
     */
    private fun getCityName(lat: Double,long: Double):String{
        var cityName:String = ""
        var countryName = ""
        var geoCoder = Geocoder(requireActivity(), Locale.ENGLISH)
        var Adress = geoCoder.getFromLocation(lat,long,3)

        if (Adress.get(0).locality !== null) {
            cityName = Adress.get(0).locality
        } else {
            cityName = ""
        }

        if (Adress.get(0).countryName !== null) {
            countryName = Adress.get(0).countryName
        } else {
            cityName = ""
        }


        var location = "${cityName}, ${countryName}"

        country.name = countryName
        country.location = "${cityName}, ${countryName}"

        textView.text = location

        return location
    }

    object country {
        var name = ""
        var location = ""
    }


    override fun onLocationChanged(location: Location) {
        Log.d("debug", "called!")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item!!, requireView().findNavController()) || super.onOptionsItemSelected(item)
    }
}