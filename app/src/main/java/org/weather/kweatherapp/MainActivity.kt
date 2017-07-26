package org.weather.kweatherapp

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.Observer
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.android.synthetic.main.activity_main.current_weather as currentWeatherView
import kotlinx.android.synthetic.main.activity_main.location as locationView

class MainActivity : LifecycleActivity(), Observer<Address?> {
    companion object {
        const val PERMISSION_REQUEST_CODE = 1
    }

    private lateinit var locationRepository: LocationRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        locationRepository = LocationRepository(FusedLocationProviderClient(this), Geocoder(this))
    }

    override fun onResume() {
        super.onResume()
        hideStatusBar()

        if (locationPermissionIsGranted()) {
            startObservingLocation()
        } else {
            requestLocationPermission()
        }
    }

    override fun onStop() {
        stopObservingLocation()
        super.onStop()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (locationPermissionIsGrantedOnRuntime(requestCode, grantResults)) {
            locationRepository.currentLocation.observe(this, this)
        }
    }

    override fun onChanged(t: Address?) {
        locationView.text = t?.locality ?: "-"
    }

    private fun startObservingLocation() {
        locationRepository.currentLocation.observe(this, this)
    }

    private fun stopObservingLocation() {
        locationRepository.currentLocation.removeObserver(this)
    }
}
