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
        Assert.assertTrue(convertTime(0,0)==0L)
    }

    fun convertTime(hours : Int,minutes : Int ):Long {
        return 0L
    }
}