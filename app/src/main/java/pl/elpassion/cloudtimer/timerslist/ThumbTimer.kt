package pl.elpassion.cloudtimer.timerslist

import android.widget.TextView
import com.triggertrap.seekarc.SeekArc
import pl.elpassion.cloudtimer.TimeConverter

class ThumbTimer(val textField: TextView, val arcCounter: SeekArc) {
    private var arcValue = -1

    var time: Long = 0L
        set(time) {
            textField.text = TimeConverter.formatShortFromMilliToMinutes(time)
            if (arcValue != TimeConverter.getMinutes(time).toInt()) {
                arcCounter.progress = TimeConverter.getMinutes(time).toInt() + 1
                arcValue = TimeConverter.getMinutes(time).toInt() + 1
            }
        }

    fun setOnClickListener(listener : () -> Unit) {
        textField.setOnClickListener{ listener }
    }
}