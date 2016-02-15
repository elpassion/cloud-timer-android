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
    }

    @Test
    fun shouldNotifyAboutChangesWhenTimerHasFinished() {
        adapter.updateTimers(listOf(Timer("timer", 1000000)))
        adapter.registerAdapterDataObserver(observer)
        currentTimeInMillis = { System.currentTimeMillis() + 2000000 }
        adapter.handleTimersStateChange()
        assertEquals(listOf(ChangeOp(0, 1)), operations)
    }
}