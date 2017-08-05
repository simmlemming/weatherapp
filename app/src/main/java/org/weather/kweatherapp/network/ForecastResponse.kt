package org.weather.kweatherapp.network

import org.weather.kweatherapp.forecast.WeatherForecast
import org.weather.kweatherapp.weather.Weather

class ForecastResponse {
    var list: List<ForecastPart>? = null

    class ForecastPart {
        var main: WeatherResponse.MainPart? = null
        var weather: List<WeatherResponse.WeatherPart>? = null
        var dt: Long? = null

        fun toWeather(): Weather {
            return Weather("-",
                    weather?.firstOrNull()?.id ?: -1,
                    dt.toCalendar(),
                    main?.temp?.toInt() ?: 0,
                    main?.humidity?.toInt() ?: 0,
                    main?.pressure?.toInt() ?: 0,
                    weather?.firstOrNull()?.icon ?: "")
        }
    }

    fun toWeatherForecast(): WeatherForecast {
        val weather = list
                ?.filterIndexed { index, _ -> index > 0 } // first forecast is (kind of) current weather
                ?.map { it.toWeather() } ?: listOf<Weather>()
        return WeatherForecast(weather)
    }
}