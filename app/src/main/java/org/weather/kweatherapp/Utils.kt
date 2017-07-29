package org.weather.kweatherapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Address
import android.location.Location
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.PermissionChecker
import android.view.View
import com.google.android.gms.location.FusedLocationProviderClient
import java.util.*

fun MainActivity.locationPermissionIsGranted() : Boolean {
    return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PermissionChecker.PERMISSION_GRANTED
}

fun MainActivity.requestLocationPermission() {
    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), MainActivity.PERMISSION_REQUEST_CODE)
}

fun MainActivity.locationPermissionIsGrantedOnRuntime(requestCode: Int, grantResults: IntArray): Boolean {
    return requestCode == MainActivity.PERMISSION_REQUEST_CODE && grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED

}
fun Location?.toAddress(): Address? {
    if (this == null) {
        return null
    }

    val address = Address(Locale.getDefault())
    address.latitude = latitude
    address.longitude = longitude
    return address
}

//fun Location?.requestDetails(geocoder : Geocoder, callback: (Address?) -> Unit) {
//    let {
//        geocoder.getFromLocationAsync(this!!.latitude, longitude, callback)
//    }
//}

fun Activity.hideStatusBar() {
    val decorView = window.decorView
    val uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
    decorView.systemUiVisibility = uiOptions
}

@SuppressLint("MissingPermission")
fun FusedLocationProviderClient.requestLastKnownLocation(callback : (Location?) -> Unit) {
    lastLocation.addOnSuccessListener(callback)
}

//fun Geocoder.getFromLocationAsync(lat: Double, lon: Double, callback: (Address?) -> Unit) {
//    object : AsyncTask<Pair<Double, Double>, Nothing, Address?>() {
//        override fun doInBackground(vararg coords: Pair<Double, Double>?): Address? {
//            val (lat, lon) = coords[0]!!
//            return getFromLocation(lat, lon, 1).firstOrNull()
//        }
//
//        override fun onPostExecute(result: Address?) {
//            callback.invoke(result)
//        }
//    }.execute(Pair(lat, lon))
//}
