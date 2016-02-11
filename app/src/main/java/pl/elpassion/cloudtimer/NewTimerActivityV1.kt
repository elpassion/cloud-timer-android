package pl.elpassion.cloudtimer

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import com.triggertrap.seekarc.SeekArc


class NewTimerActivityV1 : Activity(){

    val seekArcMinutes by lazy { findViewById(R.id.timer_seekArc_minutes) as SeekArc }
    val timerDurationMinutes by lazy { findViewById(R.id.timer_duration_minutes) as TextView }
    val timerDurationSeconds by lazy { findViewById(R.id.timer_duration_seconds) as TextView }
    val timerTimeToEnd by lazy { findViewById(R.id.timer_time_to_end) as TextView }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_timer_layout_v1)
        seekArcMinutes.setOnSeekArcChangeListener(SeekArcMinutesChangeListener())
    }

    inner class SeekArcMinutesChangeListener : SeekArc.OnSeekArcChangeListener {

        override fun onProgressChanged(p0: SeekArc?, currentValue: Int, p2: Boolean) {

            val currentTimerValue = Math.round(currentValue/360f*59)
            timerDurationMinutes.text = currentTimerValue.toString()
            val timerDurationInMilis = currentTimerValue * 60 * 1000
            val currentTime = System.currentTimeMillis()
            timerDurationSeconds.text = ":00"
            timerTimeToEnd.text = TimeConverter.formatFromMilliToTime(currentTime + timerDurationInMilis)
        }

        override fun onStartTrackingTouch(p0: SeekArc?) {
        }

        override fun onStopTrackingTouch(p0: SeekArc?) {
        }

    }
}