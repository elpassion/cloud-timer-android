package pl.elpassion.cloudtimer.timerslist

import pl.elpassion.cloudtimer.adapter.BaseAdapter
import pl.elpassion.cloudtimer.domain.Timer

class NewAdapter : BaseAdapter() {
    fun updateTimers(timers: List<Timer>) {
        if (adapters.size > 0) {
            if (adapters.size < timers.size) {
                notifyItemRangeInserted(0, timers.size - adapters.size)
                notifyItemRangeChanged(timers.size - adapters.size, adapters.size)
                return
            } else {
                notifyItemRangeChanged(0, timers.size)
                return
            }
        }
        adapters.addAll(createAdaptersForCloudTimerItems(timers))
        notifyItemRangeInserted(0, timers.size)
    }
}