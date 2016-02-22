package pl.elpassion.cloudtimer.group

import pl.elpassion.cloudtimer.adapter.BaseAdapter
import pl.elpassion.cloudtimer.adapter.ItemAdapter
import pl.elpassion.cloudtimer.domain.Timer
import java.util.*

internal fun createAdaptersForCloudTimerItems(timers: List<Timer>): ArrayList<ItemAdapter> {
    val itemsAdapters = ArrayList<ItemAdapter>()
    timers.forEach { itemsAdapters.add(NewGroupTimerItemAdapter(it)) }
    return itemsAdapters
}

class NewGroupListOfTimersAdapter : BaseAdapter() {
    fun updateTimers(timers: List<Timer>) {
        addNewTimers(timers)
    }

    private fun addNewTimers(timers: List<Timer>) {
        adapters.clear()
        adapters.addAll(createAdaptersForCloudTimerItems(timers))
    }
}