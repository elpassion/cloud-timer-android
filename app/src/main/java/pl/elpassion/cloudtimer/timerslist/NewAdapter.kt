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
        val timerAdapters = adapters.filter { it is TimerItemAdapter }
        val mappedTimerAdapters = timerAdapters.map { it as TimerItemAdapter }
        val countOfFinished = mappedTimerAdapters.count { it.timer.finished }
        if (countOfFinished > 0) {
            for (i in countOfFinished - 1 downTo 0) {
                val notFinishedAdapter = adapters[i] as TimerItemAdapter
                val finishedTimerItemAdapter = FinishedTimerItemAdapter(notFinishedAdapter.timer)
                val countOfTimerItemAdapters = adapters.filter { it is TimerItemAdapter }.size
                adapters.add(countOfTimerItemAdapters, finishedTimerItemAdapter)
                adapters.removeAt(i)
            }
            if (timerAdapters.size > countOfFinished) {
                for (i in 0..countOfFinished - 1)
                    notifyItemMoved(0, countOfFinished )
            } else {
                notifyItemRangeChanged(0, countOfFinished)
            }
        }
    }

}
