package pl.elpassion.cloudtimer.timer

import android.content.Context
import android.util.AttributeSet
import com.triggertrap.seekarc.SeekArc

class CustomSeekArc(context: Context, atrrs: AttributeSet?, defStyle:Int) : SeekArc(context, atrrs, defStyle) {

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, atrrs: AttributeSet ) : this(context, atrrs, 0)

    private val beginRange: IntRange = 0..10
    private val endRange: IntRange = 50..59
    var previousTimerValue: Int = 0
    var hoursValue: Int = 0
    var timerDurationInMillis: Long = 15 * 60 * 1000

    fun setTimerHourValue(currentTimerValue: Int) {
        if (currentTimerValue in beginRange && previousTimerValue.toInt() in endRange) {
            hoursValue++
        } else if (previousTimerValue in beginRange && currentTimerValue in endRange) {
            if (hoursValue > 0)
                hoursValue--
        }
    }
}

inline fun seekArcChangeListener(crossinline updateView: (CustomSeekArc) -> Unit) = object : SeekArc.OnSeekArcChangeListener {
    override fun onProgressChanged(seekArc: SeekArc, currentValue: Int, isChanged: Boolean) {
        val customSeekArc = seekArc as CustomSeekArc
        val currentTimerValue = Math.round(currentValue / 360f * 59)
        val currentTimerValueInMillis = currentTimerValue * 60L * 1000L
        customSeekArc.setTimerHourValue(currentTimerValue)
        customSeekArc.timerDurationInMillis = (customSeekArc.hoursValue * 60 * 60 * 1000) + currentTimerValueInMillis
        updateView(customSeekArc)
        customSeekArc.previousTimerValue = currentTimerValue
    }

    override fun onStartTrackingTouch(seekArc: SeekArc?) {
    }

    override fun onStopTrackingTouch(seekArc: SeekArc?) {
    }
}