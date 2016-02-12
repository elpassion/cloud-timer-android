package pl.elpassion.cloudtimer.timerslist

import android.support.v7.widget.RecyclerView
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import pl.elpassion.cloudtimer.domain.Timer
import java.util.*


class TimersListActionsTest {

    val adapter = NewAdapter()
    val operations = ArrayList<Pair<Int, Int>>()
    val observer = object : RecyclerView.AdapterDataObserver() {
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            operations.add(Pair(positionStart, itemCount))
        }
    }

    @Before
    fun registerObserver() {
        adapter.registerAdapterDataObserver(observer)
    }

    @Test
    fun whenSingleItemIsAddedNotifyItemOnPositionZeroInserted() {
        adapter.updateTimers(listOf(Timer("test", 1000)))
        assertEquals(listOf(Pair(0, 1)), operations)
    }

    @Test
    fun whenTwoItemsAreAddedNotifyItemsOnPositionZeroAndOneInserted() {
        adapter.updateTimers(listOf(Timer("test", 1000), Timer("test2", 2000)))
        assertEquals(listOf(Pair(0, 2)), operations)
    }
}