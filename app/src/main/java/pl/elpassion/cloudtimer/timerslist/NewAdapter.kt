package pl.elpassion.cloudtimer.timerslist

import pl.elpassion.cloudtimer.adapter.BaseAdapter
import pl.elpassion.cloudtimer.domain.Timer

class NewAdapter : BaseAdapter() {
    fun updateTimers(timers: List<Timer>) {
        if (timers.size >= adapters.size){
            notifyItemRangeInserted(0, timers.size - adapters.size)
            notifyItemRangeChanged(timers.size - adapters.size, adapters.size)
            adapters.clear()
            adapters.addAll(createAdaptersForCloudTimerItems(timers))
        } else {
            notifyItemRangeRemoved(0, adapters.size - timers.size )
            notifyItemRangeChanged(0, timers.size)
        }
    }
}