package pl.elpassion.cloudtimer.timer

import org.junit.Assert
import org.junit.Test
import pl.elpassion.cloudtimer.domain.Timer
import pl.elpassion.cloudtimer.timerslist.NewAdapter

class TimersListAdapterTest {

    @Test
    fun ifRangeOfNotFinishedTimersIsCorrect() {
        val adapter = NewAdapter()
        adapter.updateTimers(listOf(Timer("timer", 100000)))
        val range = adapter.getNotFinishedTimersRange()
        Assert.assertEquals(0..0, range)
    }

    @Test
    fun ifRangeOfTwoNotFinishedTimersIsCorrect(){
        val adapter = NewAdapter()
        adapter.updateTimers(listOf(Timer("timer", 100000),Timer("timer", 100000)))
        val range = adapter.getNotFinishedTimersRange()
        Assert.assertEquals(0..1, range)
    }

    @Test
    fun ifRangeOfOneFinishedTimerIsCorrect(){
        val adapter = NewAdapter()
        adapter.updateTimers(listOf(Timer("timer", -100000)))
        val range = adapter.getNotFinishedTimersRange()
        Assert.assertEquals(0..-1, range)
    }
}