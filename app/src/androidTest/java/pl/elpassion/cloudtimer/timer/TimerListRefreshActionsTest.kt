package pl.elpassion.cloudtimer.timer

import android.support.v7.widget.RecyclerView
import org.junit.Assert.assertEquals
import org.junit.Test
import pl.elpassion.cloudtimer.currentTimeInMillis
import pl.elpassion.cloudtimer.domain.Timer
import pl.elpassion.cloudtimer.timerslist.ChangeOp
import pl.elpassion.cloudtimer.timerslist.InsertedOp
import pl.elpassion.cloudtimer.timerslist.NewAdapter
import pl.elpassion.cloudtimer.timerslist.RemoveOp
import java.util.*

class TimerListRefreshActionsTest {

    val adapter = NewAdapter()
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

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            operations.add(MoveOp(fromPosition, toPosition, itemCount))
        }
    }

    @Test
    fun shouldNotifyAboutChangesWhenTimerHasFinished() {
        adapter.updateTimers(listOf(Timer("timer", 1000000)))
        adapter.registerAdapterDataObserver(observer)
        currentTimeInMillis = { System.currentTimeMillis() + 2000000 }
        adapter.handleTimersStateChange()
        assertEquals(listOf(ChangeOp(0, 1)), operations)
    }

    @Test
    fun shouldNotNotifyAboutChangesWhenTimerHasNotFinished() {
        adapter.updateTimers(listOf(Timer("timer", 1000000)))
        adapter.registerAdapterDataObserver(observer)
        adapter.handleTimersStateChange()
        assertEquals(emptyList<Any>(), operations)
    }

    @Test
    fun shouldNotifyAboutOneChangeWhenOnlyOneTimerHasFinished() {
        adapter.updateTimers(listOf(Timer("timer", 1000000), Timer("timer", 500)))
        adapter.registerAdapterDataObserver(observer)
        currentTimeInMillis = { System.currentTimeMillis() + 700 }
        adapter.handleTimersStateChange()
        assertEquals(listOf(MoveOp(0, 1, 1)), operations)
    }

    @Test
    fun shouldNotNotifyWhenTimersHasNotChangeState() {
        adapter.updateTimers(listOf(Timer("timer", -1000)))
        adapter.registerAdapterDataObserver(observer)
        currentTimeInMillis = { System.currentTimeMillis() + 2000 }
        adapter.handleTimersStateChange()
        assertEquals(emptyList<Any>(), operations)
    }

    @Test
    fun shouldNotifyOnlyWhenTimersHasJustFinished() {
        adapter.updateTimers(listOf(Timer("timer", -100000), Timer("tiemr not finished", 100000)))
        adapter.registerAdapterDataObserver(observer)
        currentTimeInMillis = { System.currentTimeMillis() + 200000 }
        adapter.handleTimersStateChange()
        assertEquals(listOf(ChangeOp(0, 1)), operations)
    }
}

data class MoveOp(val from: Int, val to: Int, val count: Int)
