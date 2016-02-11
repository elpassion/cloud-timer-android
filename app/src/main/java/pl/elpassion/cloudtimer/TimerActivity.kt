package pl.elpassion.cloudtimer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.NumberPicker
import pl.elpassion.cloudtimer.alarm.scheduleAlarm
import pl.elpassion.cloudtimer.domain.Timer

class TimerActivity : Activity() {

    companion object {
        fun start(activity: Activity, timerActivityResultCode: Int) {
            val intent = Intent(activity, TimerActivity::class.java)
            activity.startActivityForResult(intent, timerActivityResultCode)
        }
    }

    private val hoursPicker by lazy { findViewById(R.id.hours_picker)as NumberPicker }
    private val minutesPicker by lazy { findViewById(R.id.minutes_picker) as NumberPicker }
    private val secondsPicker by lazy { findViewById(R.id.seconds_picker) as NumberPicker }
    private val startButton by lazy { findViewById(R.id.start_button) as Button }
    private val timerTitle by lazy { findViewById(R.id.timer_title) as EditText }
    private val alarmDao by lazy { TimerDAO.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
        setUpTimePickers()
        setUpButtons()
    }

    private fun setUpTimePickers() {
        hoursPicker.setValues(0, 24, 0)
        minutesPicker.setValues(0, 59, 15)
        secondsPicker.setValues(0, 59, 0)
    }

    private fun setUpButtons() {
        startButton.setOnClickListener {
            // TODO: TMP create timer
            val newTimer = Timer(timerTitle.text.toString(), getTime())
            scheduleAlarm(newTimer, this)
            alarmDao.save(newTimer)
            finish()
        }
    }

    private fun getTime(): Long {
        return convertTime(hoursPicker.value, minutesPicker.value, secondsPicker.value)
    }

    private fun NumberPicker.setValues(minVal: Int, maxVal: Int, defaultValue: Int) {
        minValue = minVal
        maxValue = maxVal
        wrapSelectorWheel = true
        value = defaultValue
    }
}