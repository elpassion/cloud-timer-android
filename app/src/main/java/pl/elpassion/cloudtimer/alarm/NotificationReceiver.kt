package pl.elpassion.cloudtimer.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.util.Log


class NotificationReceiver : BroadcastReceiver() {
    companion object {
        var player : MediaPlayer = MediaPlayer() // todo // <----

        var displayNotification = fun (context : Context) {
            createNotificationManager("Title", "text", context)
            playSound(context)
        }

        fun playSound(context : Context) {
            player.setAudioStreamType(AudioManager.STREAM_ALARM)
            player.setDataSource(context, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
            player.prepare()
            player.start()
            player.setOnCompletionListener { mediaPlayer -> mediaPlayer.release() }
        }

    }

    override fun onReceive(context : Context, intent: Intent) {
        stopSound()
        Log.e("CATLOG", "stop sound")
    }

    private fun stopSound() {
        player.stop()
        player.release()
    }

}

