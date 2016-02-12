package pl.elpassion.cloudtimer.timerslist

import android.support.v7.widget.RecyclerView
import org.junit.Assert.assertEquals
import org.junit.Test
import pl.elpassion.cloudtimer.domain.Timer
import java.util.*


class TimersListActionsTest {

    val adapter = NewAdapter()
    val operations = ArrayList<Pair<Int, Int>>()
    val changes = ArrayList<Pair<Int, Int>>()
    val observer = object : RecyclerView.AdapterDataObserver() {
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            operations.add(Pair(positionStart, itemCount))
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            changes.add(Pair(positionStart, itemCount))
        }
    }

    fun registerObserver() {
        adapter.registerAdapterDataObserver(observer)
    }

    @Test
    fun whenSingleItemIsAddedNotifyItemOnPositionZeroInserted() {
        registerObserver()
        adapter.updateTimers(listOf(Timer("test", 1000)))
        assertEquals(listOf(Pair(0, 1)), operations)
    }

    @Test
    fun whenTwoItemsAreAddedNotifyItemsOnPositionZeroAndOneInserted() {
        registerObserver()
        adapter.updateTimers(listOf(Timer("test", 1000), Timer("test2", 2000)))
        assertEquals(listOf(Pair(0, 2)), operations)
    }

    @Test
    fun whenTimersWereUpdatedNotifyItemsWereUpdated() {
        adapter.updateTimers(listOf(Timer("test", 1000)))
        registerObserver()
        adapter.updateTimers(listOf(Timer("test", 1000)))
        assertEquals(listOf(Pair(0, 1)), changes)
    }

    @Test
    fun whenTwoTimersAreUpdatedNotifyItemsTwoItemsWereUpdated() {
        adapter.updateTimers(listOf(Timer("test", 1000), Timer("test2", 2000)))
        registerObserver()
        adapter.updateTimers(listOf(Timer("test", 1000), Timer("test2", 2000)))
        assertEquals(listOf(Pair(0, 2)), changes)
    }
}