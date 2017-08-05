package org.weather.kweatherapp.forecast

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import org.weather.kweatherapp.R
import org.weather.kweatherapp.relativeHours
import org.weather.kweatherapp.weather.Weather
import org.weather.kweatherapp.weather.WeatherView
import java.util.*

/**
 * Created by mtkachenko on 05/08/17.
 */
class WeatherForecastItemView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs), WeatherView {
    private lateinit var dateView : TextView

    override fun onFinishInflate() {
        super.onFinishInflate()
        View.inflate(context, R.layout.weather_forecast_background_item, this)

        dateView = findViewById<TextView>(R.id.date)
    }
    override fun setWeather(weather: Weather) {
//        dateView.setBackgroundColor(getWeatherColor(weather.id))
        val hours = weather.date.relativeHours(Calendar.getInstance())
        dateView.text = "+${hours}h"
    }
}