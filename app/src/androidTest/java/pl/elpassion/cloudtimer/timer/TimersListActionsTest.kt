package pl.elpassion.cloudtimer.timerslist

import android.support.v7.widget.RecyclerView
import org.junit.Assert.assertEquals
import org.junit.Test
import pl.elpassion.cloudtimer.domain.Timer
import java.util.*


class TimersListActionsTest {

    val adapter = ListOfTimersAdapter()
    val operations = ArrayList<Any>()
    val observer = object : RecyclerView.AdapterDataObserver() {
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            operations.add(InsertedOp(positionStart, itemCount))
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            operations.add(ChangeOp(positionStart, itemCount))
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            operations.add(RemoveOp(positionStart, itemCount))
        }
    }

    fun registerObserver() {
        adapter.registerAdapterDataObserver(observer)
    }

    @Test
    fun whenSingleItemIsAddedNotifyItemOnPositionZeroInserted() {
        registerObserver()
        adapter.updateTimers(listOf(Timer("test", 1000)))
        assertEquals(listOf(InsertedOp(0, 1)), operations)
    }

    @Test
    fun whenTwoItemsAreAddedNotifyItemsOnPositionZeroAndOneInserted() {
        registerObserver()
        adapter.updateTimers(listOf(Timer("test", 1000), Timer("test2", 2000)))
        assertEquals(listOf(InsertedOp(0, 2)), operations)
    }

    @Test
    fun whenTimersWereUpdatedNotifyItemsWereUpdated() {
        adapter.updateTimers(listOf(Timer("test", 1000)))
        registerObserver()
        adapter.updateTimers(listOf(Timer("test", 1000)))
        assertEquals(listOf(ChangeOp(0, 1)), operations)
    }

    @Test
    fun whenTwoTimersAreUpdatedNotifyItemsTwoItemsWereUpdated() {
        adapter.updateTimers(listOf(Timer("test", 1000), Timer("test2", 2000)))
        registerObserver()
        adapter.updateTimers(listOf(Timer("test", 1000), Timer("test2", 2000)))
        assertEquals(listOf(ChangeOp(0, 2)), operations)
    }

    @Test
    fun whenOneTimerIsReplaceByTwoTimersNotifyAboutOneUpdateAndOneInsert() {
        adapter.updateTimers(listOf(Timer("test", 1000)))
        registerObserver()
        adapter.updateTimers(listOf(Timer("test", 1000), Timer("test", 1000)))
        assertEquals(listOf(InsertedOp(0, 1), ChangeOp(1, 1)), operations)
    }

    @Test
    fun shouldContainCorrectNumberOfItems() {
        adapter.updateTimers(listOf(Timer("test", 1000)))
        adapter.updateTimers(listOf(Timer("test", 1000), Timer("test", 1000)))
        assertEquals(2, adapter.itemCount)
    }

    @Test
    fun whenTimerIsRemovedShouldNotifyAboutIt() {
        adapter.updateTimers(listOf(Timer("test", 1000), Timer("test", 1000)))
        registerObserver()
        adapter.updateTimers(listOf(Timer("test", 1000)))
        assertEquals(listOf(RemoveOp(0, 1), ChangeOp(0, 1)), operations)
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

data class RemoveOp(val first: Int, val count: Int)

data class ChangeOp(val first: Int, val count: Int)

data class InsertedOp(val first: Int, val count: Int)