package pl.elpassion.cloudtimer

import android.app.IntentService
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.util.Log

class AlarmService() : IntentService("alarm-service") {
    override fun onHandleIntent(intent: Intent?) {
        Log.e("ALARM", "Intent service started")
        playAlarm()
    }

    private fun playAlarm() {
        val player = MediaPlayer()
        player.setAudioStreamType(AudioManager.STREAM_ALARM)
        player.setDataSource(this,
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
        player.prepare()
        player.start()
        // TODO?
        player.release()
    }
}