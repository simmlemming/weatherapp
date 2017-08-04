package org.weather.kweatherapp

import android.arch.lifecycle.MutableLiveData
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit

abstract class ScheduledLiveData<T>(private val executor : ScheduledThreadPoolExecutor) : MutableLiveData<T>(), Runnable {
    abstract val interval : Long
    abstract val timeUnit : TimeUnit

    private var futureExecution: ScheduledFuture<*>? = null

    override fun onActive() {
        scheduleSelf()
    }

    override fun onInactive() {
        unscheduleSelf()
    }

    protected fun scheduleSelf() {
        futureExecution = executor.scheduleAtFixedRate(this, 0, interval, timeUnit)
    }

    protected fun unscheduleSelf() {
        futureExecution?.cancel(true)
        futureExecution = null
        executor.purge()
    }
}