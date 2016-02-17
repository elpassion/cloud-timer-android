package pl.elpassion.cloudtimer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import pl.elpassion.cloudtimer.alarm.startNextTimerSchedule


class BootCompleteReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        Log.e("BOOT COMPLETED", "Device is started")
        startNextTimerSchedule()
    }

}