package pl.elpassion.cloudtimer.timerslist

import pl.elpassion.cloudtimer.adapter.BaseAdapter
import pl.elpassion.cloudtimer.adapter.ItemAdapter
import pl.elpassion.cloudtimer.domain.Timer
import java.util.*

internal fun createAdaptersForCloudTimerItems(timers: List<Timer>): ArrayList<ItemAdapter> {
    val itemsAdapters = ArrayList<ItemAdapter>()
    val (finished, notFinished) = timers.partition { it.finished }
    notFinished.sortedBy { it.endTime }.forEach { itemsAdapters.add(TimerItemAdapter(it)) }
    finished.sortedByDescending { it.endTime }.forEach { itemsAdapters.add(FinishedTimerItemAdapter(it)) }
    return itemsAdapters
}

class ListOfTimersAdapter : BaseAdapter() {

    fun updateTimers(timers: List<Timer>) {
        if (timers.size > adapters.size) {
            notifyItemRangeInserted(0, timers.size - adapters.size)
            if (adapters.size > 0)
                notifyItemRangeChanged(timers.size - adapters.size, adapters.size)
        } else if (timers.size < adapters.size) {
            notifyItemRangeRemoved(0, adapters.size - timers.size)
            notifyItemRangeChanged(0, timers.size)
        } else {
            notifyItemRangeChanged(0, adapters.size)
        }
        addNewTimers(timers)
    }

    private fun addNewTimers(timers: List<Timer>) {
        adapters.clear()
        adapters.addAll(createAdaptersForCloudTimerItems(timers))
    }

    fun getNotFinishedTimersRange(): IntRange {
        return 0..adapters.indexOfLast { it is TimerItemAdapter }
    }

    fun handleTimersStateChange() {
        val currentTimerAdapters = adapters.filter { it is TimerItemAdapter }.map { it as TimerItemAdapter }
        val (finished, notFinished) = currentTimerAdapters.partition { it.timer.finished }
        if (finished.size > 0) {
            val asFinished = finished.map { FinishedTimerItemAdapter(it.timer) }
            val newAdapters = notFinished + asFinished + adapters.filter { it is FinishedTimerItemAdapter }
            adapters.clear()
            adapters.addAll(newAdapters)
            if (notFinished.size > 0) {
                for (i in 0..finished.lastIndex)
                    notifyItemMoved(0, currentTimerAdapters.lastIndex)
            } else {
                notifyItemRangeChanged(0, finished.size)
            }
        }
    }

}
