package pl.elpassion.cloudtimer.timerslist

import pl.elpassion.cloudtimer.adapter.BaseAdapter
import pl.elpassion.cloudtimer.domain.Timer

class NewAdapter :BaseAdapter(){
    fun updateTimers(timers: List<Timer>) {
        adapters.addAll(createAdaptersForCloudTimerItems(timers))
        notifyItemInserted(0)
    }
}