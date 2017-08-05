package org.weather.kweatherapp.weather

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import org.weather.kweatherapp.R
import kotlinx.android.synthetic.main.weather_widget.view.humidity as humidityView
import kotlinx.android.synthetic.main.weather_widget.view.icon as iconView
import kotlinx.android.synthetic.main.weather_widget.view.pressure as pressureView
import kotlinx.android.synthetic.main.weather_widget.view.temp as tempView

/**
 * Created by mtkachenko on 26/07/17.
 */
class WeatherWidget(context: Context, attributeSet: AttributeSet) : FrameLayout(context, attributeSet), WeatherView {

    override fun onFinishInflate() {
        super.onFinishInflate()
        inflate(context, R.layout.weather_widget, this)
    }

    override fun setWeather(weather: Weather) {
        tempView.text = formatTemperature(context, weather.temp)
        pressureView.text = formatPressure(context, weather.pressure)
        humidityView.text = formatHumidity(context, weather.humidity)
        iconView.setImageResource(getIconResourceId(weather.icon))
    }
}

