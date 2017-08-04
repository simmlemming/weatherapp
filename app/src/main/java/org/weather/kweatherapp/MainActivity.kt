package org.weather.kweatherapp

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import org.weather.kweatherapp.forecast.WeatherForecast
import org.weather.kweatherapp.weather.Weather
import kotlinx.android.synthetic.main.activity_main.current_weather as currentWeatherView

class MainActivity : LifecycleActivity() {
    companion object {
        const val PERMISSION_REQUEST_CODE = 1
    }

    private lateinit var viewModel : MainViewModel

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
        currentWeatherView.setTemperature(weather?.temp ?: 0)
        currentWeatherView.setPressure(weather?.pressure ?: 0)
        currentWeatherView.setHumidity(weather?.humidity ?: 0)
        currentWeatherView.setIcon(weather?.icon ?: "")
    }

    private fun onNewWeatherForecast(forecast: WeatherForecast?) {
        Log.i("W", forecast.toString())
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
