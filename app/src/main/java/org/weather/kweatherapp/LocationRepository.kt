package org.weather.kweatherapp

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.location.Address
import android.location.Geocoder
import com.google.android.gms.location.FusedLocationProviderClient

/**
 * Created by mtkachenko on 26/07/17.
 */
class LocationRepository(private val locationClient: FusedLocationProviderClient, private val geocoder: Geocoder) {
    val currentLocation: LiveData<Address?> = CurrentLocationLiveData()

    private inner class CurrentLocationLiveData : MutableLiveData<Address?>() {
        override fun onActive() {
            requestLastKnownLocation()
        }

        private fun requestLastKnownLocation() {
            locationClient.requestLastKnownLocation { location ->
                value = location.toAddress()

                location.requestDetails(geocoder) { address ->
                    value = address
                }
            }
        }
    }
}
