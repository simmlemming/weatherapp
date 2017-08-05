package org.weather.kweatherapp.forecast

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import org.weather.kweatherapp.R
import org.weather.kweatherapp.weather.WeatherView
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by mtkachenko on 05/08/17.
 */
class WeatherForecastBackground(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs), WeatherForecastView {
    private lateinit var weatherViews: List<WeatherView>

    override fun onFinishInflate() {
        super.onFinishInflate()
        View.inflate(context, R.layout.weather_forecast_background_widget, this)

        val rootView = findViewById<ViewGroup>(R.id.root)
        weatherViews = (0 until rootView.childCount)
                .map { rootView.getChildAt(it) }
                .filterIsInstance<WeatherView>()
    }

    override fun setWeatherForecast(forecast: WeatherForecast) {
        weatherViews.forEachIndexed { index, weatherView ->
            weatherView.setWeather(forecast.weather[index])
            (weatherView as View).setWeight(forecast.durationMin(index))
        }
    }
}

fun View.setWeight(weight: Int) {
    (layoutParams as LinearLayout.LayoutParams).weight = weight + 0f
    layoutParams = layoutParams
}

fun WeatherForecast.durationMin(index: Int): Int {
    val prevMs = if (index == 0) {
        Calendar.getInstance().timeInMillis
    } else {
        weather[index - 1].date?.timeInMillis ?: 0
    }

    val thisMs = weather[index].date?.timeInMillis ?: 0
    return TimeUnit.MINUTES.convert(thisMs - prevMs, TimeUnit.MILLISECONDS).toInt()
}