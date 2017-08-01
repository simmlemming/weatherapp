package org.weather.kweatherapp

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.current_weather as currentWeatherView

class MainActivity : LifecycleActivity(), Observer<WeatherInfo?> {
    companion object {
        const val PERMISSION_REQUEST_CODE = 1
    }

    private lateinit var viewModel : MainViewModel;

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
            startObservingWeather()
        } else {
            requestLocationPermission()
        }
    }

    override fun onPause() {
        stopObservingWeather()
        super.onPause()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (locationPermissionIsGrantedOnRuntime(requestCode, grantResults)) {
            startObservingWeather()
        }
    }

    override fun onChanged(weather: WeatherInfo?) {
        currentWeatherView.setTemperature(weather?.temp ?: 0)
        currentWeatherView.setPressure(weather?.pressure ?: 0)
        currentWeatherView.setHumidity(weather?.humidity ?: 0)
        currentWeatherView.setIcon(weather?.icon ?: "")
    }

    private fun startObservingWeather() {
        viewModel.currentWeather.observe(this, this)
    }

    private fun stopObservingWeather() {
        viewModel.currentWeather.removeObserver(this)
    }
}
