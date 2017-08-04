package org.weather.kweatherapp.forecast

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.weather.kweatherapp.R
import org.weather.kweatherapp.weather.Weather
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by mtkachenko on 04/08/17.
 */
class WeatherForecastWidget(context: Context, attributeSet: AttributeSet?) : RecyclerView(context, attributeSet) {
    private var lastForecast = WeatherForecast.EMPTY

    override fun onFinishInflate() {
        super.onFinishInflate()
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        adapter = WeatherForecastAdapter()
    }

    fun setWeatherForecast(forecast: WeatherForecast) {
        lastForecast = forecast
        adapter.notifyDataSetChanged()
    }

    private inner class WeatherForecastAdapter : RecyclerView.Adapter<WeatherForecastViewHolder>() {
        private val layoutInflater = LayoutInflater.from(context)

        override fun getItemCount(): Int {
            return lastForecast.weather.size
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): WeatherForecastViewHolder {
            val itemView = layoutInflater.inflate(R.layout.weather_forecast_list_item, parent, false)
            return WeatherForecastViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: WeatherForecastViewHolder, position: Int) {
            holder.setWeather(lastForecast.weather[position])
        }
    }

    private inner class WeatherForecastViewHolder(itemView: View) : ViewHolder(itemView) {
        private val tempView = itemView.findViewById<TextView>(R.id.temp)
        private val dateView = itemView.findViewById<TextView>(R.id.date)

        fun setWeather(weather : Weather) {
            tempView.text = context.getString(R.string.temp_format, weather.temp)
            val hours = weather.date.relativeHours(Calendar.getInstance())
            dateView.text = "+${hours}h"
        }
    }
}

fun Calendar?.relativeHours(other : Calendar) : Int {
    if (this == null) {
        return 0
    }

    val differenceMs = timeInMillis - other.timeInMillis
    return TimeUnit.HOURS.convert(differenceMs, TimeUnit.MILLISECONDS).toInt()
}