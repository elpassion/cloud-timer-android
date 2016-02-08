package pl.elpassion.cloudtimer.domain

import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test


class TimerTests {

    private lateinit var timer: Timer

    @Before
    fun before() {
        timer = Timer(
                title = "",
                endTime = 0,
                duration = 0
        )
    }

    @Test
    fun isNotSharedWhenHasNoGroup() {
        assertFalse(timer.isShared())
    }

    @Test
    fun isPausedWhenTimeLeftIsNull() {
        assertFalse(timer.isPaused())
    }


}