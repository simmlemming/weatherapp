package org.weather.kweatherapp

import android.app.Application
import com.google.android.gms.location.FusedLocationProviderClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by mtkachenko on 29/07/17.
 */

class WeatherApplication : Application() {
    val locationRepository by lazy { LocationRepository(FusedLocationProviderClient(this)) }
    val weatherApi : WeatherApi by lazy { buildWeatherApi() }

    private fun buildWeatherApi() : WeatherApi {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

        val builder = Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return builder.create(WeatherApi::class.java)
    }
}
