package pl.elpassion.cloudtimer

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.triggertrap.seekarc.SeekArc
import pl.elpassion.cloudtimer.alarm.scheduleAlarm
import pl.elpassion.cloudtimer.domain.Timer


class TimerActivity : Activity() {

    val seekArcMinutes by lazy { findViewById(R.id.timer_seekArc_minutes) as SeekArc }
    val timerDuration by lazy { findViewById(R.id.timer_duration) as TextView }
    val timerTimeToEnd by lazy { findViewById(R.id.timer_time_to_end) as TextView }
    val startTimerButton by lazy { findViewById(R.id.start_timer_button) as Button }
    val timerTitle by lazy { findViewById(R.id.new_timer_title) as EditText }
    protected val alarmDao by lazy { TimerDAO.getInstance(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
        seekArcMinutes.setOnSeekArcChangeListener(SeekArcMinutesChangeListener())

        startTimerButton.setOnClickListener {
            val newTimer = Timer(timerTitle.text.toString(), getTime())
            scheduleAlarm(newTimer, this)
            alarmDao.save(newTimer)
            finish()
        }
    }

    private fun getTime() : Long {
        return convertTime(12, 50, 11)
    }

    inner class SeekArcMinutesChangeListener : SeekArc.OnSeekArcChangeListener {

        var previousTimerValue: Int = 0
        var hoursValue: Int = 0
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