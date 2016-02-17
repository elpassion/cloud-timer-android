package pl.elpassion.cloudtimer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.triggertrap.seekarc.SeekArc
import pl.elpassion.cloudtimer.alarm.scheduleAlarm
import pl.elpassion.cloudtimer.domain.Timer

class TimerActivity : AppCompatActivity() {

    companion object {
        fun start(activity: Activity, timerActivityResultCode: Int) {
            val intent = Intent(activity, TimerActivity::class.java)
            activity.startActivityForResult(intent, timerActivityResultCode)
        }
    }

    val timerSeekArc by lazy { findViewById(R.id.timer_seekArc) as SeekArc }
    val timerDuration by lazy { findViewById(R.id.timer_duration) as TextView }
    val timerEndTime by lazy { findViewById(R.id.timer_time_to_end) as TextView }
    val startTimerButton by lazy { findViewById(R.id.start_timer_button) as Button }
    val timerTitle by lazy { findViewById(R.id.new_timer_title) as EditText }
    val timerToolbar by lazy { findViewById(R.id.new_timer_toolbar) as Toolbar }
    val beginRange: IntRange = 0..10
    val endRange: IntRange = 50..59
    var previousTimerValue: Int = 0
    var hoursValue: Int = 0
    protected val alarmDao by lazy { TimerDAO.getInstance() }
    var timerDurationInMilis: Long = 0
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
        startTimerButton.text = "START"
        timerDurationInMilis = 15 * 60 * 1000
        timerSeekArc.setOnSeekArcChangeListener(SeekArcMinutesChangeListener())
        refreshTimerEndTime()
        timerToolbar.inflateMenu(R.menu.timer_menu)

        startTimerButton.setOnClickListener {
            val newTimer = Timer(timerTitle.text.toString(), timerDurationInMilis)
            scheduleAlarm(newTimer, this)
            alarmDao.save(newTimer)
            finish()
        }
    }

    override fun onResume() {
        handler.postDelayed(TimerEndTimeRefresher(), 1000)
        timerDuration.text = "15:00"
        refreshTimerEndTime()
        super.onResume()
    }

    override fun onStop() {
        handler.removeCallbacks(TimerEndTimeRefresher())
        super.onStop()
    }

    private fun refreshTimerEndTime() {
        val endTime = System.currentTimeMillis() + timerDurationInMilis
        timerEndTime.text = TimeConverter.formatFromMilliToTime(endTime)
    }

    private fun setTimerHourValue(currentTimerValue: Int) {
        if (currentTimerValue in beginRange && previousTimerValue.toInt() in endRange) {
            hoursValue++
        } else if (previousTimerValue in beginRange && currentTimerValue in endRange) {
            hoursValue--
            if (hoursValue < 0) {
                hoursValue = 0
            }
        }
    }

    private fun onSeekBarTimeChange(currentValue: Int) {
        val currentTime = System.currentTimeMillis()
        val currentTimerValue = Math.round(currentValue / 360f * 59)
        val currentTimerValueInMilis = currentTimerValue * 60L * 1000L

        setTimerHourValue(currentTimerValue)

        timerDurationInMilis = (hoursValue * 60 * 60 * 1000) + currentTimerValueInMilis
        val timerEndTimeInMilis = currentTime + timerDurationInMilis
        timerEndTime.text = TimeConverter.formatFromMilliToTime(timerEndTimeInMilis)
        timerDuration.text = TimeConverter.formatFromMilliToMinutes(timerDurationInMilis.toLong())
        previousTimerValue = currentTimerValue
    }


    inner class SeekArcMinutesChangeListener : SeekArc.OnSeekArcChangeListener {

        override fun onProgressChanged(seekArc: SeekArc?, currentValue: Int, isChanged: Boolean) {
            onSeekBarTimeChange(currentValue)
        }

        override fun onStartTrackingTouch(seekArc: SeekArc?) {
        }

        override fun onStopTrackingTouch(seekArc: SeekArc?) {
        }
    }

    inner class TimerEndTimeRefresher : Runnable {
        override fun run() {
            refreshTimerEndTime()
            handler.postDelayed(this, 1000)
        }
    }
}