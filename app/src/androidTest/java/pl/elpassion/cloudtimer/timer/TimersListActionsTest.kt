package pl.elpassion.cloudtimer.timerslist

import android.support.v7.widget.RecyclerView
import org.junit.Assert.assertEquals
import org.junit.Test
import pl.elpassion.cloudtimer.domain.Timer
import java.util.*


class TimersListActionsTest {

    @Test
    fun initTest() {
        val operations = ArrayList<Pair<Int, Int>>()
        val adapter = NewAdapter()
        val observer = object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                operations.add(Pair(positionStart, itemCount))
            }
        }
        adapter.registerAdapterDataObserver(observer)
        adapter.updateTimers(listOf(Timer("test", 1000)))
        assertEquals(listOf(Pair(0, 1)), operations)
    }
}