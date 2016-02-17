package pl.elpassion.cloudtimer.alarm

import android.content.Context.MODE_PRIVATE
import pl.elpassion.cloudtimer.CloudTimerApp.Companion.applicationContext

object NotificationIdGenerator {

    private val sharedPreferencesKey = "pl.elpassion.cloud-timer"
    private val idKey = "notificationId"
    val sharedPreferences = applicationContext.getSharedPreferences(sharedPreferencesKey, MODE_PRIVATE)

    fun incrementAndGet(): Int {
        val id = getIncremented()
        return id
    }

    private fun getIncremented(): Int = sharedPreferences.getInt(idKey, 0) + 1

}

