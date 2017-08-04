package org.weather.kweatherapp.network

import okhttp3.Interceptor
import okhttp3.Response
import org.weather.kweatherapp.forecast.WeatherForecast
import org.weather.kweatherapp.weather.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

/**
 * Created by mtkachenko on 29/07/17.
 */
interface WeatherApi {
    @GET("weather")
    fun requestWeather(@Query("lat") lat: Double, @Query("lon") lon: Double): Call<WeatherResponse>

    @GET("forecast")
    fun requestForecast(@Query("lat") lat: Double, @Query("lon") lon: Double): Call<ForecastResponse>
}

class WeatherResponse {
    var name: String = "-"
    var main: Main? = null
    var weather : List<Weather>? = null

    class Main {
        var temp: Double = 0.0
        var pressure: Double = 0.0
        var humidity: Double = 0.0
    }

    class Weather {
        var icon : String? = null
    }

    fun toWeather(): org.weather.kweatherapp.weather.Weather {
        return Weather(name,
                main?.temp?.toInt() ?: 0,
                main?.humidity?.toInt() ?: 0,
                main?.pressure?.toInt() ?: 0,
                weather?.firstOrNull()?.icon ?: "")
    }
}

class ForecastResponse {
    var list : List<Forecast>? = null

    class Forecast {
        var main : WeatherResponse.Main? = null
        var weather : List<WeatherResponse.Weather>? = null
        var dt : Long? = null
    }

    fun toWeatherForecast() : WeatherForecast {
        return WeatherForecast(Date())
    }
}

class WeatherApiParams : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val url = request.url().newBuilder()
        url.addQueryParameter("APPID", "4761c5b7cf1e4a93793287e2c8fc37ee")
        url.addQueryParameter("units", "metric")

        val requestWithParams = request
                .newBuilder()
                .url(url.build())
                .build()

        return chain.proceed(requestWithParams)
    }
}