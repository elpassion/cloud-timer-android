package pl.elpassion.cloudtimer

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import com.triggertrap.seekarc.SeekArc


class NewTimerActivityV1 : Activity() {

    val seekArcMinutes by lazy { findViewById(R.id.timer_seekArc_minutes) as SeekArc }
    val timerDuration by lazy { findViewById(R.id.timer_duration) as TextView }
    val timerTimeToEnd by lazy { findViewById(R.id.timer_time_to_end) as TextView }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_timer_layout_v1)
        seekArcMinutes.setOnSeekArcChangeListener(SeekArcMinutesChangeListener())
    }

    inner class SeekArcMinutesChangeListener : SeekArc.OnSeekArcChangeListener {

        var previousTimerValue: Int = 0
        var hoursValue : Int = 0
        val beginRange = 0..10
        val endRange = 50..59

        override fun onProgressChanged(seekArc: SeekArc?, currentValue: Int, isChanged: Boolean) {

            val currentTime = System.currentTimeMillis()
            val currentTimerValue = Math.round(currentValue / 360f * 59)
            val currentTimerValueInMilis = currentTimerValue * 60 * 1000

            if (currentTimerValue in beginRange && previousTimerValue.toInt() in endRange) {
                hoursValue++
            } else if (previousTimerValue in beginRange && currentTimerValue in endRange) {
                hoursValue--
                if (hoursValue < 0) {
                    hoursValue = 0
                }
            }

            val durationInMilis = (hoursValue * 60 * 60 * 1000) + currentTimerValueInMilis
            val timerEndTimeInMilis = currentTime + durationInMilis
            timerTimeToEnd.text = TimeConverter.formatFromMilliToTime(timerEndTimeInMilis)
            timerDuration.text = TimeConverter.formatFromMilliToMinutes(durationInMilis.toLong())
            previousTimerValue = currentTimerValue
        }

        override fun onStartTrackingTouch(p0: SeekArc?) {
        }

        override fun onStopTrackingTouch(p0: SeekArc?) {
        }

    }
}