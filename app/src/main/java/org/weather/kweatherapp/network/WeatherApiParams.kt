package org.weather.kweatherapp.network

import okhttp3.Interceptor
import okhttp3.Response

class WeatherApiParams : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val url = request.url().newBuilder()
        url.addQueryParameter("APPID", "4761c5b7cf1e4a93793287e2c8fc37ee")
        url.addQueryParameter("units", "metric")

        val requestWithParams = request
                .newBuilder()
                .url(url.build())
                .build()

        return chain.proceed(requestWithParams)
    }
}