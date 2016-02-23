package pl.elpassion.cloudtimer.timerslist

import android.widget.TextView
import com.triggertrap.seekarc.SeekArc
import pl.elpassion.cloudtimer.TimeConverter

class ThumbTimer(val textField: TextView ,val arcCounter : SeekArc) {
    var text: Long = 0L

    set(time) {
        textField.text = TimeConverter.formatShortFromMilliToMinutes(time)
        arcCounter.progress = TimeConverter.getMinutes(time).toInt()
        if (TimeConverter.getHours(time) > 0) {

        }
    }

    fun <T> setOnClickListener(body: () -> T) {
        textField.setOnClickListener( { body })
    }
}