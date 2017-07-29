package org.weather.kweatherapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by mtkachenko on 29/07/17.
 */
interface WeatherApi {
    @GET("weather?APPID=4761c5b7cf1e4a93793287e2c8fc37ee&units=metric")
    fun requestWeather(@Query("lat") lat: Double, @Query("lon") lon: Double): Call<WeatherResponse>
}

class WeatherResponse {
    var name: String = "-"
    var main: Main? = null

    class Main {
        var temp: Double = 0.0
        var pressure: Int = 0
        var humidity: Int = 0

        override fun toString(): String {
            return "Main(temp=$temp, pressure=$pressure, humidity=$humidity)"
        }
    }

    fun toWeatherInfo(): WeatherInfo {
        return WeatherInfo(name,
                main?.temp?.toInt() ?: 0,
                main?.humidity ?: 0,
                main?.pressure ?: 0)
    }

    override fun toString(): String {
        return "WeatherResponse(name='$name', main=$main)"
    }
}