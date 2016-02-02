package pl.elpassion.cloudtimer

import android.app.IntentService
import android.content.Intent
import android.util.Log

class AlarmService() : IntentService("alarm-service") {
    override fun onHandleIntent(intent: Intent?) {
        Log.e("ALARM", "Intent service")
    }
}