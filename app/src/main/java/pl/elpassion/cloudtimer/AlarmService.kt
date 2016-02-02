package pl.elpassion.cloudtimer

import android.app.IntentService
import android.content.Intent

/**
 * Created by jasiekpor on 02.02.2016.
 */
class AlarmService() : IntentService("alarm-service") {
    override fun onHandleIntent(intent: Intent?) {
        throw UnsupportedOperationException()
    }
}