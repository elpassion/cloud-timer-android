package pl.elpassion.cloudtimer

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker

class TimerActivity : Activity() {
    val hoursPicker by lazy { findViewById(R.id.hours_picker)as NumberPicker }
    val minutesPicker by lazy { findViewById(R.id.minutes_picker) as NumberPicker }
    val startButton by lazy { findViewById(R.id.start_button) as Button }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
        hoursPicker.setValues(0, 24, 0)
        minutesPicker.setValues(0, 60, 15)
    }

    override fun onStart() {
        super.onStart()
        startButton.setOnClickListener {
            // TODO: TMP create timer
            val newTimer = AlarmData(convertTime(hoursPicker.value, minutesPicker.value))
            scheduleAlarm(newTimer, this)
        }
    }

    private fun NumberPicker.setValues(minVal: Int, maxVal: Int, defaultValue: Int) {
        minValue = minVal
        maxValue = maxVal
        wrapSelectorWheel = true
        value = defaultValue
    }
}