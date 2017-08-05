package org.weather.kweatherapp.weather

import java.util.*

/**
 * Created by mtkachenko on 26/07/17.
 */
data class Weather(val name : String,
                   val id : Int,
                   val date : Calendar?,
                   val temp : Int,
                   val humidity : Int,
                   val pressure : Int,
                   val icon : String) {

    companion object {
        val EMPTY = Weather("-", -1, null, 0, 0, 0, "")
    }
}

