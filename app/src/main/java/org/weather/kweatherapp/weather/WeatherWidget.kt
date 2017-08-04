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

    fun setIcon(id: String) {
        iconView.setImageResource(IconMapper.map(id))
    }
}

object IconMapper {
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
