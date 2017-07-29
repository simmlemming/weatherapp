package org.weather.kweatherapp

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.location.Location
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by mtkachenko on 29/07/17.
 */
class MainViewModel(app: Application) : AndroidViewModel(app) {
    val locationRepository = (app as WeatherApplication).locationRepository
    val weatherApi = (app as WeatherApplication).weatherApi

    var currentWeather = WeatherLiveData() as LiveData<WeatherInfo?>

    private inner class WeatherLiveData : MutableLiveData<WeatherInfo?>(), retrofit2.Callback<WeatherInfo> {
        override fun onActive() {
            locationRepository.getCurrentLocation { location ->
                if (location == null) {
                    value = null
                } else {
                    requestWeather(location)
                }
            }
        }

        private fun requestWeather(location: Location) {
            Log.i("W", "Requested weather for $location")
            val call = weatherApi.requestWeather(location.latitude, location.longitude)
            call.enqueue(object : Callback<WeatherResponse> {
                override fun onFailure(c: Call<WeatherResponse>?, t: Throwable?) {
                    Log.e("W", "Cannot retrieve current weather", t)
                }

                override fun onResponse(c: Call<WeatherResponse>?, response: Response<WeatherResponse>?) {
                    Log.i("W", "Current weather + ${response?.body()}")
                    value = response?.body()?.toWeatherInfo()
                }
            })
        }

        override fun onFailure(call: Call<WeatherInfo>?, t: Throwable?) {
            Log.e("W", "Cannot retrieve current weather", t)
        }

        override fun onResponse(call: Call<WeatherInfo>?, response: Response<WeatherInfo>?) {
            Log.i("W", "Current weather + ${response?.body()}")
        }

        override fun onInactive() {

        }
    }
}

