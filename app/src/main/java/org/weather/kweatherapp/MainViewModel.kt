package org.weather.kweatherapp

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.location.Location
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.android.Main
import kotlinx.coroutines.launch
import org.weather.kweatherapp.forecast.WeatherForecast
import org.weather.kweatherapp.weather.Weather
import org.weather.kweatherapp.weather.WeatherLiveData
import java.util.concurrent.ScheduledThreadPoolExecutor

/**
 * Created by mtkachenko on 29/07/17.
 */
class MainViewModel(app: Application) : AndroidViewModel(app) {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    companion object {
        const val UPDATE_INTERVAL_SEC: Long = 10 * 60
    }

    private val locationRepository = (app as WeatherApplication).locationRepository
    private val weatherRepository = (app as WeatherApplication).weatherRepository
    private val executor = ScheduledThreadPoolExecutor(1)

    var currentWeather = CurrentWeatherLiveData() as LiveData<Weather?>
    var weatherForecast = ForecastLiveData() as LiveData<WeatherForecast?>

    private inner class ForecastLiveData : WeatherLiveData<WeatherForecast?>(locationRepository, executor) {
        override fun update(location: Location) {
            uiScope.launch {
                value = weatherRepository.getWeatherForecast(location)
            }
        }
    }

    private inner class CurrentWeatherLiveData : WeatherLiveData<Weather?>(locationRepository, executor) {
        override fun update(location: Location) {
            uiScope.launch {
                value = weatherRepository.getWeather(location)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}