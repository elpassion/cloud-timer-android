package pl.elpassion.cloudtimer.domain

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class TimerTests {

    @Test
    fun isNotFinishedWhenEndTimeIsAfterCurrentTime() {
        val oneSec: Long = 1000
        val timer = Timer("test", oneSec)
        assertFalse(timer.finished)
    }

    @Test
    fun isFinishedWhenEndTimeIsBeforeCurrentTime() {
        val minusOneSec: Long = -1000
        val timer = Timer("test", minusOneSec)
        assertTrue(timer.finished)
    }

}