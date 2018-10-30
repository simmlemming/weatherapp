package org.weather.kweatherapp.weather

import android.location.Location
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.weather.kweatherapp.forecast.WeatherForecast
import org.weather.kweatherapp.network.WeatherApi
import org.weather.kweatherapp.retryUntilSuccess

class WeatherRepository(private val api: WeatherApi) {

    suspend fun getWeather(location: Location): Weather? {
        val call = api.requestWeather(location.latitude, location.longitude)
        val response = call.retryUntilSuccess()
        return response?.body()?.toWeather()
    }

    fun getWeatherForecast(location: Location): Deferred<WeatherForecast?> {
        return GlobalScope.async {
            val call = api.requestForecast(location.latitude, location.longitude)
            val response = call.retryUntilSuccess()
            response?.body()?.toWeatherForecast()
        }
    }
}