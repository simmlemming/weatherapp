package org.weather.kweatherapp.weather

import android.location.Location
import android.util.Log
import org.weather.kweatherapp.forecast.WeatherForecast
import org.weather.kweatherapp.network.ForecastResponse
import org.weather.kweatherapp.network.WeatherApi
import org.weather.kweatherapp.network.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherRepository(private val api : WeatherApi) {

    fun getWeather(location : Location, callback : (Weather?) -> Unit) {
        api.requestWeather(location.latitude, location.longitude).enqueue(object : Callback<WeatherResponse> {
            override fun onFailure(call: Call<WeatherResponse>?, t: Throwable?) {
                Log.e("W", "Cannot retrieve current weather", t)
                callback.invoke(null)
            }

            override fun onResponse(call: Call<WeatherResponse>?, response: Response<WeatherResponse>?) {
                callback.invoke(response?.body()?.toWeather())
            }
        })
    }

    fun getWeatherForecast(location: Location, callback : (WeatherForecast?) -> Unit) {
        api.requestForecast(location.latitude, location.longitude).enqueue(object : Callback<ForecastResponse> {
            override fun onFailure(call: Call<ForecastResponse>?, t: Throwable?) {
                Log.e("W", "Cannot retrieve weather forecast", t)
                callback.invoke(null)
            }

            override fun onResponse(call: Call<ForecastResponse>?, response: Response<ForecastResponse>?) {
                callback.invoke(response?.body()?.toWeatherForecast())
            }
        })
    }
}