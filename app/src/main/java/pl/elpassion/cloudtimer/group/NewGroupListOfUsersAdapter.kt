package pl.elpassion.cloudtimer.group


import android.support.v7.widget.RecyclerView
import pl.elpassion.cloudtimer.adapter.BaseRecyclerViewAdapter
import pl.elpassion.cloudtimer.adapter.ItemAdapter
import pl.elpassion.cloudtimer.domain.User
import java.util.*

internal fun createAdaptersForCloudTimerItems(users: List<User>): ArrayList<ItemAdapter<RecyclerView.ViewHolder>> {
    val itemsAdapters = ArrayList<ItemAdapter<RecyclerView.ViewHolder>>()
    users.forEach { itemsAdapters.add(NewGroupUserItemAdapter(it)) }
    return itemsAdapters
}

class NewGroupListOfUsersAdapter : BaseRecyclerViewAdapter() {
    fun updateUsers(users: List<User>) {
        addNewTimers(users)
    }

    private fun addNewTimers(users: List<User>) {
        adapters.clear()
        adapters.addAll(createAdaptersForCloudTimerItems(users))
    }
}