package pl.elpassion.cloudtimer.timerslist

import pl.elpassion.cloudtimer.adapter.BaseAdapter
import pl.elpassion.cloudtimer.adapter.ItemAdapter
import pl.elpassion.cloudtimer.domain.Timer
import java.util.*

internal fun createAdaptersForCloudTimerItems(timers: List<Timer>): ArrayList<ItemAdapter> {
    val itemsAdapters = ArrayList<ItemAdapter>()
    val partition = timers.sortedBy { it.endTime }
            .partition { it.finished }
    partition.second.forEach { itemsAdapters.add(TimerItemAdapter(it)) }
    partition.first.forEach { itemsAdapters.add(FinishedTimerItemAdapter(it)) }
    return itemsAdapters
}

class TimersListAdapter(val timers: List<Timer>) : BaseAdapter() {

    init {
        this.adapters.addAll(createAdaptersForCloudTimerItems(timers))
    }
}