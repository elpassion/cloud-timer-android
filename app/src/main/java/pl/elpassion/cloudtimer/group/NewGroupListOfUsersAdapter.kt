package pl.elpassion.cloudtimer.group


import pl.elpassion.cloudtimer.adapter.BaseAdapter
import pl.elpassion.cloudtimer.adapter.ItemAdapter
import pl.elpassion.cloudtimer.domain.User
import java.util.*

internal fun createAdaptersForCloudTimerItems(users: List<User>): ArrayList<ItemAdapter> {
    val itemsAdapters = ArrayList<ItemAdapter>()
    users.forEach { itemsAdapters.add(NewGroupUserItemAdapter(it)) }
    return itemsAdapters
}

class NewGroupListOfUsersAdapter : BaseAdapter() {
    fun updateUsers(users: List<User>) {
        addNewTimers(users)
    }

    private fun addNewTimers(users: List<User>) {
        adapters.clear()
        adapters.addAll(createAdaptersForCloudTimerItems(users))
    }
}