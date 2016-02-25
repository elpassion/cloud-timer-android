package pl.elpassion.cloudtimer.group

import android.support.v7.widget.RecyclerView
import pl.elpassion.cloudtimer.adapter.BaseRecyclerViewAdapter
import pl.elpassion.cloudtimer.adapter.ItemAdapter
import pl.elpassion.cloudtimer.domain.Timer
import java.util.*

internal fun createAdaptersForCloudTimerItems(timers: List<Timer>): ArrayList<ItemAdapter<RecyclerView.ViewHolder>> {
    val itemsAdapters = ArrayList<ItemAdapter<RecyclerView.ViewHolder>>()
    timers.forEach { itemsAdapters.add(NewGroupTimerItemAdapter(it)) }
    return itemsAdapters
}

class NewGroupListOfTimersAdapter : BaseRecyclerViewAdapter() {
    fun updateTimers(timers: List<Timer>) {
        addNewTimers(timers)
    }

    private fun addNewTimers(timers: List<Timer>) {
        adapters.clear()
        adapters.addAll(createAdaptersForCloudTimerItems(timers))
    }
}