package pl.elpassion.cloudtimer.timerslist

import pl.elpassion.cloudtimer.adapter.BaseAdapter
import pl.elpassion.cloudtimer.domain.Timer

class NewAdapter : BaseAdapter() {

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
                    notifyItemMoved(0, finished.size)
            } else {
                notifyItemRangeChanged(0, finished.size)
            }
        }
    }

}
