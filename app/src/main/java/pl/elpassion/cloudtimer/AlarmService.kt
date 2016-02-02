package pl.elpassion.cloudtimer

import android.app.IntentService
import android.content.Intent
import android.util.Log

/**
 * Created by jasiekpor on 02.02.2016.
 */
class AlarmService() : IntentService("alarm-service") {
    override fun onHandleIntent(intent: Intent?) {
        Log.e("ALARM", "Intent service")
    }
}