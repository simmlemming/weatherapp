package org.weather.kweatherapp

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import retrofit2.Call
import retrofit2.Response
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
    private val weatherApi = (app as WeatherApplication).weatherApi

    var currentWeather = WeatherLiveData() as LiveData<WeatherInfo?>

    private inner class WeatherLiveData : MutableLiveData<WeatherInfo?>(), retrofit2.Callback<WeatherResponse>, Runnable {
        val executor = ScheduledThreadPoolExecutor(1)
        private var futureWeatherRequest: ScheduledFuture<*>? = null

        override fun onActive() {
            Log.i("W", "onActive()")
            futureWeatherRequest = executor.scheduleAtFixedRate(this, 0, UPDATE_INTERVAL_MIN, TimeUnit.MINUTES)
        }

        override fun run() {
            locationRepository.getCurrentLocation { location ->
                if (location == null) {
                    value = null
                } else {
                    weatherApi
                            .requestWeather(location.latitude, location.longitude)
                            .enqueue(this)
                }
            }
        }

        override fun onFailure(c: Call<WeatherResponse>?, t: Throwable?) {
            Log.e("W", "Cannot retrieve current weather", t)
        }

        override fun onResponse(c: Call<WeatherResponse>?, response: Response<WeatherResponse>?) {
            value = response?.body()?.toWeatherInfo()
        }

        override fun onInactive() {
            futureWeatherRequest?.cancel(true)
            futureWeatherRequest = null
            executor.purge()
        }
    }
}

