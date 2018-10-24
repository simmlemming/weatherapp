package org.weather.kweatherapp.weather

import android.location.Location
import org.weather.kweatherapp.MainViewModel
import org.weather.kweatherapp.ScheduledLiveData
import org.weather.kweatherapp.location.LocationRepository
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit

abstract class WeatherLiveData<T>(private val locationRepository: LocationRepository, executor: ScheduledThreadPoolExecutor) : ScheduledLiveData<T>(executor) {
    override val interval = MainViewModel.UPDATE_INTERVAL_SEC
    override val timeUnit = TimeUnit.SECONDS

    override fun run() {
        locationRepository.getCurrentLocation { location ->
            if (location == null) {
                value = null
            } else {
                update(location)
            }
        }
    }

    protected abstract fun update(location: Location)
}