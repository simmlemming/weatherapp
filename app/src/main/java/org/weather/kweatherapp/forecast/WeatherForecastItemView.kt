package org.weather.kweatherapp.forecast

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import org.weather.kweatherapp.R
import org.weather.kweatherapp.weather.Weather
import org.weather.kweatherapp.weather.WeatherView
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by mtkachenko on 05/08/17.
 */
open class WeatherForecastItemView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs), WeatherView {
    private companion object {
        val DATE_FORMAT = SimpleDateFormat("HH:mm", Locale.getDefault())
    }

    protected lateinit var dateView: TextView
    protected lateinit var timeMarkerViewBlock: View

    override fun onFinishInflate() {
        super.onFinishInflate()
        View.inflate(context, R.layout.weather_forecast_background_item, this)

        timeMarkerViewBlock = findViewById(R.id.time_marker_block)
        dateView = findViewById(R.id.time_marker)
    }

    override fun setWeather(weather: Weather) {
        setBackgroundColor(weather.id.toWeatherColor(context))
        dateView.text = DATE_FORMAT.format(weather.date?.time)
//        val minutes = weather.date.between(Calendar.getInstance(), TimeUnit.MINUTES)
//        dateView.text = "+${minutes.toHumanReadableTime()}"
    }
}

private fun Int.toWeatherColor(context: Context): Int {
    val colorResId = when (this) {
        in 200..299 -> R.color.weather_thunder
        in 300..399 -> R.color.weather_drizzle
        in 500..599 -> R.color.weather_rain
        in 600..699 -> R.color.weather_snow
        in 700..799 -> R.color.weather_fog
        in 800..800 -> R.color.weather_clear
        in 801..809 -> R.color.weather_clouds
        in 900..999 -> R.color.weather_extreme
        else -> R.color.weather_unknown
    }

    return context.resources.getColor(colorResId)
}

private fun Int.toHumanReadableTime(): String {
    val hours = this / 60
    val minutes = this % 60
    return String.format("%02d:%02d", hours, minutes)
}