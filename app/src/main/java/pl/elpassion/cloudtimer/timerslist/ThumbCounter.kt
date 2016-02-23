package pl.elpassion.cloudtimer.timerslist

import java.util.concurrent.TimeUnit
import android.widget.TextView
import com.triggertrap.seekarc.SeekArc
import pl.elpassion.cloudtimer.TimeConverter


class ThumbCounter(val textField: TextView ,val arcCounter : SeekArc) {
    var text: Long = 0L

    set(text) {
        textField.text = TimeConverter.formatFromMilliToMinutes(text)
        arcCounter.progress = TimeUnit.MINUTES.convert(text, TimeUnit.MILLISECONDS).toInt() //todo minuty?
    }

    fun <T> setOnClickListener(body: () -> T) {
        textField.setOnClickListener( { body })
    }
}