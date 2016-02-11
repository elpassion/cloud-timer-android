package pl.elpassion.cloudtimer

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import com.triggertrap.seekarc.SeekArc


class NewTimerActivityV1 : Activity(){

    val seekArcMinutes by lazy { findViewById(R.id.timer_seekArc_minutes) as SeekArc }
    val timerDuration by lazy { findViewById(R.id.timer_duration_minutes) as TextView }
    val timerTimeToEnd by lazy { findViewById(R.id.timer_time_to_end) as TextView }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_timer_layout_v1)
        seekArcMinutes.setOnSeekArcChangeListener(SeekArcMinutesChangeListener())
    }

    inner class SeekArcMinutesChangeListener : SeekArc.OnSeekArcChangeListener {

        override fun onProgressChanged(p0: SeekArc?, p1: Int, p2: Boolean) {
            timerDuration.text = p1.toString() + ":00"
            val timerDurationInMilis = p1 * 60 * 1000
            val currentTime = System.currentTimeMillis()
            timerTimeToEnd.text = TimeConverter.formatFromMilliToTime(currentTime + timerDurationInMilis)
        }

        override fun onStartTrackingTouch(p0: SeekArc?) {
        }

        override fun onStopTrackingTouch(p0: SeekArc?) {
        }

    }
}