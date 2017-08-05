package org.weather.kweatherapp

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import org.weather.kweatherapp.forecast.WeatherForecast
import org.weather.kweatherapp.forecast.WeatherForecastBackground
import org.weather.kweatherapp.forecast.WeatherForecastView
import org.weather.kweatherapp.weather.Weather
import org.weather.kweatherapp.weather.WeatherView
import org.weather.kweatherapp.weather.WeatherWidget

class MainActivity : LifecycleActivity() {
    companion object {
        const val PERMISSION_REQUEST_CODE = 1
    }

    private lateinit var weatherForecastView: WeatherForecastView
    private lateinit var currentWeatherView : WeatherView

    private lateinit var viewModel: MainViewModel

    private var weatherForecastObserver = Observer<WeatherForecast?> { forecast ->
        onNewWeatherForecast(forecast)
    }

    private var currentWeatherObserver = Observer<Weather?> { weather ->
        onNewCurrentWeather(weather)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        keepScreenOn()

        currentWeatherView = findViewById<WeatherWidget>(R.id.current_weather)
        weatherForecastView = findViewById<WeatherForecastBackground>(R.id.weather_forecast)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        hideStatusBar()

        if (locationPermissionIsGranted()) {
            startObserving()
        } else {
            requestLocationPermission()
        }
    }

    override fun onPause() {
        stopObserving()
        super.onPause()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (locationPermissionIsGrantedOnRuntime(requestCode, grantResults)) {
            startObserving()
        }
    }

    private fun onNewCurrentWeather(weather: Weather?) {
        currentWeatherView.setWeather(weather ?: Weather.EMPTY)
    }

    private fun onNewWeatherForecast(forecast: WeatherForecast?) {
        weatherForecastView.setWeatherForecast(forecast ?: WeatherForecast.EMPTY)
    }

    private fun startObserving() {
        viewModel.currentWeather.observe(this, currentWeatherObserver)
        viewModel.weatherForecast.observe(this, weatherForecastObserver)
    }

    private fun stopObserving() {
        viewModel.currentWeather.removeObserver(currentWeatherObserver)
        viewModel.weatherForecast.removeObserver(weatherForecastObserver)
    }
}
