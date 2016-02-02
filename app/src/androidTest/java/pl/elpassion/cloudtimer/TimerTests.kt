package pl.elpassion.cloudtimer

import android.support.test.runner.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by jasiekpor on 02.02.2016.
 */
@RunWith(AndroidJUnit4::class)
class TimerTests {

    @Test
    fun testTimeConvertion() {
        Assert.assertTrue(convertTime(0, 0) == 0L)
    }

    @Test
    fun testTimeConversion1Hour() {
        Assert.assertTrue(convertTime(1, 0) == 3600000L)
    }

    @Test
    fun testTimeConversion1Minute() {
        Assert.assertTrue(convertTime(0, 1) == 60000L)
    }

    @Test
    fun testTimeConversion2Hours30Minutes() {
        Assert.assertTrue(convertTime(2, 30) == 2*3600000L+30*60000L)
    }

    fun convertTime(hours: Int, minutes: Int): Long {
        return hours * 3600000L + minutes * 60000L
    }
}