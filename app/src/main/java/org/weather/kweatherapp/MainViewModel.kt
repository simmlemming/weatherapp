package org.weather.kweatherapp

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.location.Location
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.weather.kweatherapp.forecast.WeatherForecast
import org.weather.kweatherapp.weather.Weather
import org.weather.kweatherapp.weather.WeatherLiveData
import java.util.concurrent.ScheduledThreadPoolExecutor
import kotlin.coroutines.CoroutineContext

/**
 * Created by mtkachenko on 29/07/17.
 */
class MainViewModel(app: Application) : AndroidViewModel(app), CoroutineScope {
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

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
            launch {
                value = weatherRepository.getWeatherForecast(location).await()
            }
        }
    }

    private inner class CurrentWeatherLiveData : WeatherLiveData<Weather?>(locationRepository, executor) {
        override fun update(location: Location) {
            launch {
                value = weatherRepository.getWeather(location)
            }
        }
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}