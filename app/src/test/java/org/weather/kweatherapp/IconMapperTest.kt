package org.weather.kweatherapp

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class IconMapperTest {

    @Test
    fun test_map() {
        assertEquals(R.drawable.ic_w01d, IconMapper.map("01d"))
        assertEquals(R.drawable.ic_w02d, IconMapper.map("02d"))
        assertEquals(R.drawable.ic_w03d, IconMapper.map("03d"))
        assertEquals(R.drawable.ic_w04d, IconMapper.map("04d"))
        assertEquals(R.drawable.ic_w09d, IconMapper.map("09d"))
        assertEquals(R.drawable.ic_w10d, IconMapper.map("10d"))
        assertEquals(R.drawable.ic_w13d, IconMapper.map("13d"))
        assertEquals(R.drawable.ic_w50d, IconMapper.map("50d"))

        assertEquals(R.drawable.ic_w01n, IconMapper.map("01n"))
        assertEquals(R.drawable.ic_w02n, IconMapper.map("02n"))
        assertEquals(R.drawable.ic_w03d, IconMapper.map("03n"))
        assertEquals(R.drawable.ic_w04d, IconMapper.map("04n"))
        assertEquals(R.drawable.ic_w09d, IconMapper.map("09n"))
        assertEquals(R.drawable.ic_w10n, IconMapper.map("10n"))
        assertEquals(R.drawable.ic_w13d, IconMapper.map("13n"))
        assertEquals(R.drawable.ic_w50d, IconMapper.map("50n"))

        assertEquals(R.drawable.ic_w_unknown, IconMapper.map("dkjsdkf"))
    }
}
