package pl.elpassion.cloudtimer

import android.support.test.runner.AndroidJUnit4
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by jasiekpor on 02.02.2016.
 */
@RunWith(AndroidJUnit4::class)
class TimeConversionTests {

    @Test
    fun testTimeConvertion() {
        assertTrue(convertTime(0, 0, 0) == 0L)
    }

    @Test
    fun testTimeConversion1Hour() {
        assertTrue(convertTime(1, 0, 0) == 3600000L)
    }

    @Test
    fun testTimeConversion1Minute() {
        assertTrue(convertTime(0, 1, 0) == 60000L)
    }

    @Test
    fun testTimeConversion2Hours30Minutes() {
        assertTrue(convertTime(2, 30, 0) == 2 * 3600000L + 30 * 60000L)
    }

    @Test
    fun testTimeConversion2Hours30Minutes15Seconds() {
        assertTrue(convertTime(2, 30, 15) == 2 * 3600000L + 30 * 60000L + 15 * 1000L)
    }

}