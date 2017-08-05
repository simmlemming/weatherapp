package org.weather.kweatherapp.network

import org.weather.kweatherapp.weather.Weather
import java.util.*

class WeatherResponse {
    var name: String = "-"
    var main: MainPart? = null
    var weather: List<WeatherPart>? = null
    var dt: Long? = null

    class MainPart {
        var temp: Double = 0.0
        var pressure: Double = 0.0
        var humidity: Double = 0.0
    }

    class WeatherPart {
        var icon: String? = null
        var id : Int = -1
    }

    fun toWeather(): Weather {
        return Weather(name,
                weather?.firstOrNull()?.id ?: -1,
                dt.toCalendar(),
                main?.temp?.toInt() ?: 0,
                main?.humidity?.toInt() ?: 0,
                main?.pressure?.toInt() ?: 0,
                weather?.firstOrNull()?.icon ?: "")
    }
}

fun Long?.toCalendar(): Calendar? {
    if (this == null) {
        return null
    }

    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this * 1000
    return calendar
}