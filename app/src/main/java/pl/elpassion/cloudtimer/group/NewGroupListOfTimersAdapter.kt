package pl.elpassion.cloudtimer.group

import pl.elpassion.cloudtimer.adapter.BaseRecyclerViewAdapter
import pl.elpassion.cloudtimer.domain.Timer

class NewGroupListOfTimersAdapter : BaseRecyclerViewAdapter() {
    fun updateTimers(timers: List<Timer>) {
        val timerItemAdapters = timers.map(::NewGroupTimerItemAdapter)
        adapters.clear()
        adapters.addAll(timerItemAdapters)
    }
}