package pl.elpassion.cloudtimer.alarm

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.RingtoneManager

object AlarmMediaPlayer {
    var player : MediaPlayer = MediaPlayer() // todo // <----

    fun playSound(context : Context) {
        player.setAudioStreamType(AudioManager.STREAM_ALARM)
        player.setDataSource(context, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
        player.prepare()
        player.start()
        player.setOnCompletionListener { mediaPlayer -> mediaPlayer.release() }
    }

    fun stopSound() {
        player.stop()
        player.release()
    }
}