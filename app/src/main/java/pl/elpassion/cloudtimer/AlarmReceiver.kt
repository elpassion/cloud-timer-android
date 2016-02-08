package pl.elpassion.cloudtimer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.util.Log


class AlarmReceiver : BroadcastReceiver() {
    companion object {
        public final val REQUEST_CODE = 424232
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.e("ALARM", "broadcast recieved")
        soundPlayer(context)
    }
}

var soundPlayer =
     fun (context : Context) {
        val player = MediaPlayer()
        player.setAudioStreamType(AudioManager.STREAM_ALARM)
        player.setDataSource(context,
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
        player.prepare()
        player.start()
        Log.e("ALARM", "ram pam pam")
        player.setOnCompletionListener { mediaPlayer -> mediaPlayer.release() }
    }
