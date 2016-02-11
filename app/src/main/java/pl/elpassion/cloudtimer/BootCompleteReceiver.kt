package pl.elpassion.cloudtimer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import pl.elpassion.cloudtimer.alarm.scheduleAlarm
import pl.elpassion.cloudtimer.domain.Timer


class BootCompleteReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        Log.e("BOOT COMPLETED", "Device is started")
        val currentTime = System.currentTimeMillis()
        val timerDao = TimerDAO.getInstance()
        val timers = timerDao.findAll()
        scheduleAlarmForActiveTimers(context, currentTime, timers)
    }

    private fun scheduleAlarmForActiveTimers(context: Context, currentTime: Long, timers: List<Timer>) {
        timers.filter { it.endTime > currentTime }.forEach { scheduleAlarm(it, context) }
    }
}