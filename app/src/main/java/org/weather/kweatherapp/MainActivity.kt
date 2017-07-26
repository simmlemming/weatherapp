package org.weather.kweatherapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

private fun MainActivity.hideActionBar() {
    val decorView = window.decorView
    val uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
    decorView.systemUiVisibility = uiOptions
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        hideActionBar();
    }
}
