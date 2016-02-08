package pl.elpassion.cloudtimer.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log


class AlarmReceiver : BroadcastReceiver() {
    companion object {
        final val REQUEST_CODE = 424232
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.e("ALARM", "broadcast recieved")
        createNotificationManager("Title", "text", context)
        NotificationReceiver.displayNotification(context)
    }
}


