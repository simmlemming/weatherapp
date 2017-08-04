package org.weather.kweatherapp.network

import okhttp3.Interceptor
import okhttp3.Response
import org.weather.kweatherapp.weather.WeatherInfo
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

class WeatherResponse {
    var name: String = "-"
    var main: Main? = null
    var weather : List<Weather>? = null

    class Main {
        var temp: Double = 0.0
        var pressure: Int = 0
        var humidity: Int = 0
    }

    class Weather {
        var icon : String? = null
    }

    fun toWeatherInfo(): WeatherInfo {
        return WeatherInfo(name,
                main?.temp?.toInt() ?: 0,
                main?.humidity ?: 0,
                main?.pressure ?: 0,
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