package org.weather.kweatherapp

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.weather_widget.view.humidity as humidityView
import kotlinx.android.synthetic.main.weather_widget.view.pressure as pressureView
import kotlinx.android.synthetic.main.weather_widget.view.temp as tempView

/**
 * Created by mtkachenko on 26/07/17.
 */
class WeatherWidget(context: Context, attributeSet: AttributeSet) : FrameLayout(context, attributeSet) {

    override fun onFinishInflate() {
        super.onFinishInflate()
        inflate(context, R.layout.weather_widget, this)
    }

    fun setTemperature(temp: Int) {
        tempView.text = resources.getString(R.string.temp_format, temp)
    }

    fun setPressure(pressure: Int) {
        pressureView.text = resources.getString(R.string.pressure_format, pressure)
    }

    fun setHumidity(humidity: Int) {
        humidityView.text = "$humidity %"
    }
}