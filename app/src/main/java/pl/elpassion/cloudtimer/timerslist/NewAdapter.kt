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
}