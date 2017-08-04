package org.weather.kweatherapp

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import org.weather.kweatherapp.weather.WeatherInfo
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * Created by mtkachenko on 29/07/17.
 */
class MainViewModel(app: Application) : AndroidViewModel(app) {
    private companion object {
        const val UPDATE_INTERVAL_MIN : Long = 10
    }

    private val locationRepository = (app as WeatherApplication).locationRepository
    private val weatherRepository = (app as WeatherApplication).weatherRepository

    var currentWeather = WeatherLiveData() as LiveData<WeatherInfo?>

    private inner class WeatherLiveData : MutableLiveData<WeatherInfo?>(), Runnable {
        val executor = ScheduledThreadPoolExecutor(1)
        private var futureWeatherRequest: ScheduledFuture<*>? = null

        override fun onActive() {
            futureWeatherRequest = executor.scheduleAtFixedRate(this, 0, UPDATE_INTERVAL_MIN, TimeUnit.MINUTES)
        }

        override fun run() {
            locationRepository.getCurrentLocation { location ->
                if (location == null) {
                    value = null
                } else {
                    weatherRepository.getWeather(location) { weather ->
                        value = weather
                    }
                }
            }
        }

        override fun onInactive() {
            futureWeatherRequest?.cancel(true)
            futureWeatherRequest = null
            executor.purge()
        }
    }
}

