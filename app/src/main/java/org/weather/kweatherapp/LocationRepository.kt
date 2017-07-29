package org.weather.kweatherapp

import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient

/**
 * Created by mtkachenko on 26/07/17.
 */
class LocationRepository(private val locationClient: FusedLocationProviderClient) {

    fun getCurrentLocation(callback: (Location?) -> Unit) {
        locationClient.requestLastKnownLocation { location ->
            callback(location)
        }
    }
}
