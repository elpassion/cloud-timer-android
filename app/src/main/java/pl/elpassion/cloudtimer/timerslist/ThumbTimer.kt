package pl.elpassion.cloudtimer.timerslist

import android.R
import android.widget.TextView
import com.triggertrap.seekarc.SeekArc
import pl.elpassion.cloudtimer.TimeConverter

class ThumbTimer(val textField: TextView, val arcCounter: SeekArc) {
    var time: Long = 0L

        set(time) {
            textField.text = TimeConverter.formatShortFromMilliToMinutes(time)
            if (arcValue != TimeConverter.getMinutes(time).toInt()) {
                arcCounter.progress = TimeConverter.getMinutes(time).toInt()
                if (TimeConverter.getHours(time).toInt() <= 0)
                    arcCounter.arcColor = R.color.transparent
                arcValue = TimeConverter.getMinutes(time).toInt()
            }
        }

    private var arcValue = 0

    fun <T> setOnClickListener(body: () -> T) {
        textField.setOnClickListener({ body })
    }
}