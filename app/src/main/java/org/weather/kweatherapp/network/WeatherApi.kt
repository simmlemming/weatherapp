package org.weather.kweatherapp.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by mtkachenko on 29/07/17.
 */
interface WeatherApi {
    @GET("weather")
    fun requestWeather(@Query("lat") lat: Double, @Query("lon") lon: Double): Call<WeatherResponse>

    @GET("forecast")
    fun requestForecast(@Query("lat") lat: Double, @Query("lon") lon: Double): Call<ForecastResponse>
}