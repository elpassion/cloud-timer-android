package pl.elpassion.cloudtimer.timerslist

import android.support.v7.widget.RecyclerView
import org.junit.Assert.assertEquals
import org.junit.Test
import pl.elpassion.cloudtimer.domain.Timer
import java.util.*


class TimersListActionsTest {

    val adapter = NewAdapter()
    val insertions = ArrayList<Pair<Int, Int>>()
    val changes = ArrayList<Pair<Int, Int>>()
    val operations = ArrayList<Any>()
    val observer = object : RecyclerView.AdapterDataObserver() {
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            insertions.add(Pair(positionStart, itemCount))
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            changes.add(Pair(positionStart, itemCount))
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
        assertEquals(listOf(Pair(0, 1)), insertions)
    }

    @Test
    fun whenTwoItemsAreAddedNotifyItemsOnPositionZeroAndOneInserted() {
        registerObserver()
        adapter.updateTimers(listOf(Timer("test", 1000), Timer("test2", 2000)))
        assertEquals(listOf(Pair(0, 2)), insertions)
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

    @Test
    fun whenOneTimerIsReplaceByTwoTimersNotifyAboutOneUpdateAndOneInsert() {
        adapter.updateTimers(listOf(Timer("test", 1000)))
        registerObserver()
        adapter.updateTimers(listOf(Timer("test", 1000), Timer("test", 1000)))
        assertEquals(listOf(Pair(1, 1)), changes)
        assertEquals(listOf(Pair(0, 1)), insertions)
    }

    @Test
    fun shouldContainCorrectNumberOfItems() {
        adapter.updateTimers(listOf(Timer("test", 1000)))
        adapter.updateTimers(listOf(Timer("test", 1000), Timer("test", 1000)))
        assertEquals(2, adapter.itemCount)
    }

    @Test
    fun whenTimerIsRemovedShouldNofityAboutIt() {
        adapter.updateTimers(listOf(Timer("test", 1000), Timer("test", 1000)))
        registerObserver()
        adapter.updateTimers(listOf(Timer("test", 1000)))
        assertEquals(listOf(RemoveOp(0, 1), ChangeOp(0, 1)), operations)
    }
}

data class RemoveOp(val first: Int, val count: Int)

data class ChangeOp(val first: Int, val count: Int)