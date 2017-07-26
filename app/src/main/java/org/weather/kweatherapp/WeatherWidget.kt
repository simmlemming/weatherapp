package org.weather.kweatherapp

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.weather_widget.view.icon as iconView
import kotlinx.android.synthetic.main.weather_widget.view.pressure as pressureView
import kotlinx.android.synthetic.main.weather_widget.view.temp as tempView

/**
 * Created by mtkachenko on 26/07/17.
 */
class WeatherWidget(context: Context, attributeSet: AttributeSet) : FrameLayout(context, attributeSet) {
    private val picasso: Picasso = Picasso.with(context)


    override fun onFinishInflate() {
        super.onFinishInflate()
        inflate(context, R.layout.weather_widget, this)
    }

    fun setIcon(id: String) {

    }

    fun setTemperature(temp: Int) {
        tempView.text = resources.getString(R.string.temp_format, temp)
    }

    fun setPressure(pressure: Int) {
        pressureView.text = resources.getString(R.string.pressure_format, pressure)
    }
}