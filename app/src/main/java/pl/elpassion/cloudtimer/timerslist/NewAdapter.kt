package pl.elpassion.cloudtimer.timerslist

import pl.elpassion.cloudtimer.adapter.BaseAdapter
import pl.elpassion.cloudtimer.domain.Timer

class NewAdapter : BaseAdapter() {
    fun updateTimers(timers: List<Timer>) {
        if (adapters.size > 0) {
            notifyItemChanged(0)
            return
        }
        adapters.addAll(createAdaptersForCloudTimerItems(timers))
        notifyItemRangeInserted(0, timers.size)
    }
}