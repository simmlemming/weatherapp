package org.weather.kweatherapp.location

import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient

/**
 * Created by mtkachenko on 26/07/17.
 */
class LocationRepository(private val locationClient: FusedLocationProviderClient) {

    fun getCurrentLocation(callback: (Location?) -> Unit) {
        val almere = Location("mocked")
        almere.latitude = 52.371353
        almere.longitude = 5.222124
        callback(almere)
    }
}
