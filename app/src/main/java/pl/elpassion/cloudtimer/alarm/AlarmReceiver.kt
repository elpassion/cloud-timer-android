package pl.elpassion.cloudtimer.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.domain.Timer


class AlarmReceiver : BroadcastReceiver() {
    companion object {
        final val REQUEST_CODE = 424232
        private val keyTimerTitle = "timer"

        fun create(context: Context, timer: Timer): Intent {
            val intent = Intent(context, AlarmReceiver::class.java)
            intent.putExtra(keyTimerTitle, timer.title)
            return intent
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.e("ALARM", "broadcast recieved")
        startNextTimerSchedule()
        val notifyTitle = context.resources.getString(R.string.app_name)
        val notifyText = intent.getStringExtra(keyTimerTitle)
        NotificationTools.createAndFireNotification(notifyTitle, notifyText, context)
    }
}


