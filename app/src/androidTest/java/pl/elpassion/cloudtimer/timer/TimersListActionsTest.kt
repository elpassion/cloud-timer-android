package pl.elpassion.cloudtimer.timerslist

import org.junit.Assert.assertEquals
import org.junit.Test
import pl.elpassion.cloudtimer.domain.Timer


class TimersListActionsTest {

    val adapter = ListOfTimersAdapter()

    @Test
    fun shouldContainCorrectNumberOfItems() {
        adapter.updateTimers(listOf(Timer("test", 1000)))
        adapter.updateTimers(listOf(Timer("test", 1000), Timer("test", 1000)))
        assertEquals(2, adapter.itemCount)
    }

    @Test
    fun whenEmptyListIsSendAdapterShouldHaveZeroElements() {
        adapter.updateTimers(listOf(Timer("test", 1000), Timer("test", 1000)))
        adapter.updateTimers(listOf())
        assertEquals(0, adapter.itemCount)
    }

    @Test
    fun notFinishedTimersShouldBeSorted() {
        val timers = listOf(Timer("test", 500000), Timer("test", 200000))
        adapter.updateTimers(timers)
        val timersFromAdapter = adapter.adapters.map { (it as TimerItemAdapter).timer }
        val sortedTimers = timers.sortedBy { it.duration }
        assertEquals(sortedTimers, timersFromAdapter)
    }
}

