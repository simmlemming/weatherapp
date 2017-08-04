package org.weather.kweatherapp.forecast

import org.weather.kweatherapp.weather.Weather

/**
 * Created by mtkachenko on 04/08/17.
 */
data class WeatherForecast(val weather : List<Weather>) {
    companion object {
        val EMPTY = WeatherForecast(listOf())
    }
}