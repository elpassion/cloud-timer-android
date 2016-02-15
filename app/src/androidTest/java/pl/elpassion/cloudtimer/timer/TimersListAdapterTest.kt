package pl.elpassion.cloudtimer.timer

import org.junit.Assert
import org.junit.Test
import pl.elpassion.cloudtimer.domain.Timer
import pl.elpassion.cloudtimer.timerslist.NewAdapter

class TimersListAdapterTest {

    val adapter = NewAdapter()

    @Test
    fun ifRangeOfNotFinishedTimersIsCorrect() {
        adapter.updateTimers(listOf(Timer("timer", 100000)))
        val range = adapter.getNotFinishedTimersRange()
        Assert.assertEquals(0..0, range)
    }

    @Test
    fun ifRangeOfTwoNotFinishedTimersIsCorrect() {
        adapter.updateTimers(listOf(Timer("timer", 100000), Timer("timer", 100000)))
        val range = adapter.getNotFinishedTimersRange()
        Assert.assertEquals(0..1, range)
    }

    @Test
    fun ifRangeOfOneFinishedTimerIsCorrect() {
        adapter.updateTimers(listOf(Timer("timer", -100000)))
        val range = adapter.getNotFinishedTimersRange()
        Assert.assertEquals(0..-1, range)
    }

    @Test
    fun ifRangeOfOneFinishedAndOneNotFinishedTimerIsCorrect() {
        adapter.updateTimers(listOf(Timer("timer", -100000), Timer("timer", 100000)))
        val range = adapter.getNotFinishedTimersRange()
        Assert.assertEquals(0..0, range)
    }
}