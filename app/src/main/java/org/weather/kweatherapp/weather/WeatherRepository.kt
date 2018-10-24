package org.weather.kweatherapp.weather

import android.location.Location
import org.weather.kweatherapp.forecast.WeatherForecast
import org.weather.kweatherapp.network.WeatherApi
import org.weather.kweatherapp.retryUntilSuccess

class WeatherRepository(private val api: WeatherApi) {

    suspend fun getWeather(location: Location): Weather? {
        val call = api.requestWeather(location.latitude, location.longitude)
        val response = call.retryUntilSuccess()
        return response?.body()?.toWeather()
    }

    suspend fun getWeatherForecast(location: Location): WeatherForecast? {
        val call = api.requestForecast(location.latitude, location.longitude)
        val response = call.retryUntilSuccess()
        return response?.body()?.toWeatherForecast()
    }

//    suspend fun getWeatherForecast2(location: Location): WeatherForecast? {
//        return withContext(Dispatchers.IO) {
//            val response = api.requestForecast(location.latitude, location.longitude).execute()
//            response?.body()?.toWeatherForecast()
//        }
//    }
}