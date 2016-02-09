package pl.elpassion.cloudtimer.timerslist

import pl.elpassion.cloudtimer.adapter.BaseAdapter
import pl.elpassion.cloudtimer.domain.Timer

class TimersListAdapter(val timers: List<Timer>) : BaseAdapter() {

    init {
        timers.sortedBy { it.endTime }.forEach {
            if (it is Timer){
                if (it.isShared())
                    adapters.add(SharedTimerItemAdapter(it))
                else
                    adapters.add(TimerItemAdapter(it))
            }
        }
    }

}