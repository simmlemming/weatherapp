package org.weather.kweatherapp.weather

import android.content.Context
import android.graphics.Color
import org.weather.kweatherapp.R

/**
 * Created by mtkachenko on 05/08/17.
 */
interface WeatherView {
    fun setWeather(weather: Weather)

    fun formatTemperature(context : Context, temp : Int) : String {
        return context.getString(R.string.temp_format, temp)
    }

    fun formatPressure(context : Context, pressure : Int) : String {
        return context.getString(R.string.pressure_format, pressure)
    }

    fun formatHumidity(context : Context, humidity : Int) : String {
        return "$humidity %"
    }

    fun getIconResourceId(icon : String) : Int {
        return IconMapper.map(icon)
    }

    fun getWeatherColor(weatherId : Int) : Int {
        return Color.parseColor("#999900")
    }

    private object IconMapper {
        private val ICONS = mapOf(
                "01d" to R.drawable.ic_w01d,
                "02d" to R.drawable.ic_w02d,
                "03d" to R.drawable.ic_w03d,
                "04d" to R.drawable.ic_w04d,
                "09d" to R.drawable.ic_w09d,
                "10d" to R.drawable.ic_w10d,
                "11d" to R.drawable.ic_w11d,
                "13d" to R.drawable.ic_w13d,
                "50d" to R.drawable.ic_w50d,
                "01n" to R.drawable.ic_w01n,
                "02n" to R.drawable.ic_w02n,
                "03n" to R.drawable.ic_w03d,
                "04n" to R.drawable.ic_w04d,
                "09n" to R.drawable.ic_w09d,
                "10n" to R.drawable.ic_w10n,
                "11n" to R.drawable.ic_w11d,
                "13n" to R.drawable.ic_w13d,
                "50n" to R.drawable.ic_w50d)

        fun map(id : String) : Int {
            return ICONS[id] ?: R.drawable.ic_w_unknown
        }
    }
}