package pl.elpassion.cloudtimer.timerslist

import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import pl.elpassion.cloudtimer.adapter.ItemAdapter
import pl.elpassion.cloudtimer.domain.Timer
import java.util.*

class TimersListAdapterTest {

    private val oneSec: Long = 1000
    private val minusOneSec: Long = -1000
    private val minusOneMin: Long = -1000 * 60
    private val oneMin: Long = 1000 * 60
    private val timers = listOf(
            getNotFinishedTimer(), getFinishedTimer(),
            getNotFinishedTimer(), getFinishedTimer(),
            getNotFinishedTimer(), getFinishedTimer()
    )

    private fun getTimer(duration: Long): Timer = Timer("t2", duration)
    private fun getNotFinishedTimer(): Timer = Timer("t2", oneSec)
    private fun getFinishedTimer(): Timer = Timer("t1", minusOneMin)
    private val finishedTimersList: List<Timer> = listOf(
            getTimer(minusOneMin), getTimer(minusOneMin + minusOneMin), getTimer(minusOneMin + minusOneSec))

    private val notFinishedTimersList: List<Timer> = listOf(
            getTimer(oneMin), getTimer(oneMin + oneMin), getTimer(oneMin + oneSec))

    @Test
    fun isFinishedTimerAccepted() {
        val adapters = createAdaptersForCloudTimerItems(finishedTimersList)
        assertTrue(adapters.any { it is FinishedTimerItemAdapter })
    }

    @Test
    fun isTimerAccepted() {
        val adapters = createAdaptersForCloudTimerItems(notFinishedTimersList)
        assertTrue(adapters.any { it is TimerItemAdapter })
    }

    @Test
    fun areThreeTimerAdaptersCreatedWhenThreeTimersAreGiven() {
        val adapters = createAdaptersForCloudTimerItems(notFinishedTimersList)
        assertEquals(3, countOfTimerAdapters(adapters))
    }

    private fun countOfTimerAdapters(adapters: ArrayList<ItemAdapter>): Int {
        return adapters.filter { it is TimerItemAdapter }.size
    }

    @Test
    fun areThreeFinishedTimerAdaptersCreatedWhenThreeFinishedTimersAreGiven() {
        val adapters = createAdaptersForCloudTimerItems(finishedTimersList)
        assertEquals(3, countOfFinishedTimerAdapters(adapters))
    }

    private fun countOfFinishedTimerAdapters(adapters: ArrayList<ItemAdapter>): Int {
        return adapters.filter { it is FinishedTimerItemAdapter }.size
    }

    @Test
    fun ifTimerAdaptersAreSortedCorrectly() {
        val sortedTimers = notFinishedTimersList.sortedBy { it.endTime }
        val adapters = createAdaptersForCloudTimerItems(notFinishedTimersList)
        val timersFromAdapters = adapters.map { (it as TimerItemAdapter).timer }
        assertEquals(timersFromAdapters, sortedTimers)
    }

    @Test
    fun ifFinishedTimerAdaptersAreSortedCorrectly() {
        val sortedFinishedTimers = finishedTimersList.sortedByDescending { it.endTime }
        val adapters = createAdaptersForCloudTimerItems(finishedTimersList)
        val finishedTimersFromAdapters = adapters.map { (it as FinishedTimerItemAdapter).timer }
        assertEquals(finishedTimersFromAdapters, sortedFinishedTimers)
    }

    @Test
    fun whenFinishedAndNotFinishedTimersAreGivenThereAreTwoCorrectAdapters() {
        val adapters = createAdaptersForCloudTimerItems(timers)
        Assert.assertTrue(hasTwoDifferentAdapters(adapters))
    }

    private fun hasTwoDifferentAdapters(adapters: ArrayList<ItemAdapter>): Boolean {
        return adapters.any { it is TimerItemAdapter } &&
                adapters.any { it is FinishedTimerItemAdapter }
    }

    @Test
    fun areFinishedTimersBelowNotFinishedOnes() {
        val adapters = createAdaptersForCloudTimerItems(timers)
        val isCorrectOrder = checkIfFinishedTimersAreBelowNotFinished(adapters)
        assertTrue(isCorrectOrder)
    }

    private fun checkIfFinishedTimersAreBelowNotFinished(adapters: ArrayList<ItemAdapter>): Boolean {
        return adapters.dropWhile { it is TimerItemAdapter }
                .none { it is TimerItemAdapter }
    }
}