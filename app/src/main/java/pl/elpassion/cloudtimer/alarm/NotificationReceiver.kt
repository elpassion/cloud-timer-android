package pl.elpassion.cloudtimer.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log


class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context : Context, intent: Intent) {
        Log.e("CATLOG", "intent arrived - stop sound")
        // nope for now // AlarmMediaPlayer.stopSound()
        NotificationTools.dismissNotification(context)
    }
}

