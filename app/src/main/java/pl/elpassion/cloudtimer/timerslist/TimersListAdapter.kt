package pl.elpassion.cloudtimer.timerslist

import pl.elpassion.cloudtimer.adapter.BaseAdapter
import pl.elpassion.cloudtimer.domain.Timer

class TimersListAdapter(val timers: List<Timer>) : BaseAdapter() {

    init {
        timers.sortedBy { it.endTime }.forEach {
            adapters.add(TimerItemAdapter(it))
        }
    }

}