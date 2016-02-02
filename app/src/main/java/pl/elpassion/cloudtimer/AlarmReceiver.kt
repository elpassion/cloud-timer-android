package pl.elpassion.cloudtimer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log


class AlarmReceiver : BroadcastReceiver() {
    companion object {
        public final val REQUEST_CODE = 424232
    }

    override fun onReceive(context: Context, intent: Intent) {
        // todo implementation of receiver
        Log.e("ALARM", "broadcast recieved")
    }

}