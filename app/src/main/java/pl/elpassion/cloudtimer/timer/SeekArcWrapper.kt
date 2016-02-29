package pl.elpassion.cloudtimer.timer

import com.triggertrap.seekarc.SeekArc
import pl.elpassion.cloudtimer.currentTimeInMillis
import java.util.concurrent.TimeUnit.HOURS
import java.util.concurrent.TimeUnit.MINUTES


class SeekArcWrapper(seekArc: SeekArc, val updateView: (SeekArcWrapper) -> Unit) {

    init {
        seekArc.setOnSeekArcChangeListener(SeekArcChangeListener())
    }

    private val startRange: IntRange = 0..10
    private val endRange: IntRange = 50..60
    private var previousTimerValue: Long = 0
    private var hoursValue: Long = 0
    var timerDurationInMillis: Long = MINUTES.toMillis(15)
    val endTime: Long
        get() = currentTimeInMillis() + timerDurationInMillis

    private fun setWrapperValues(currentValue: Int) {
        val minutesValue = Math.floor(currentValue / 6.0).toLong()
        setTimerHourValue(minutesValue)
        previousTimerValue = minutesValue
        timerDurationInMillis = HOURS.toMillis(hoursValue) + MINUTES.toMillis(minutesValue)
    }

    private fun setTimerHourValue(currentTimerValue: Long) {
        if (shouldAddOneHour(currentTimerValue))
            hoursValue++
        else if (shouldSubtractOneHour(currentTimerValue))
            hoursValue--
    }

    private fun shouldAddOneHour(currentTimerValue: Long) =
            (currentTimerValue in startRange) && (previousTimerValue in endRange)

    private fun shouldSubtractOneHour(currentTimerValue: Long) =
            (previousTimerValue in startRange) && (currentTimerValue in endRange) && (hoursValue > 0)


    private inner class SeekArcChangeListener : SeekArc.OnSeekArcChangeListener {
        override fun onProgressChanged(seekArc: SeekArc, currentValue: Int, isChanged: Boolean) {
            setWrapperValues(currentValue)
            updateView(this@SeekArcWrapper)
        }

        override fun onStartTrackingTouch(seekArc: SeekArc?) {
        }

        override fun onStopTrackingTouch(seekArc: SeekArc?) {
        }
    }
}