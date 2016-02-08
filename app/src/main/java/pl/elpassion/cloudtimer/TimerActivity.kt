package pl.elpassion.cloudtimer

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker

class TimerActivity : Activity() {
    val hoursPicker by lazy { findViewById(R.id.hours_picker)as NumberPicker }
    val minutesPicker by lazy { findViewById(R.id.minutes_picker) as NumberPicker }
    val secondsPicker by lazy { findViewById(R.id.seconds_picker) as NumberPicker }
    val startButton by lazy { findViewById(R.id.start_button) as Button }
    protected val alarmDao by lazy {  TimerDAO.getInstance(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
        hoursPicker.setValues(0, 24, 0)
        minutesPicker.setValues(0, 59, 15)
        secondsPicker.setValues(0,59,0)
    }

    override fun onStart() {
        super.onStart()
        startButton.setOnClickListener {
            // TODO: TMP create timer
            //val newTimer = Timer("",getTime(),System.currentTimeMillis()+getTime())
            //scheduleAlarm(newTimer, this)
            //alarmDao.save(newTimer)
        }
    }

    private fun NumberPicker.setValues(minVal: Int, maxVal: Int, defaultValue: Int) {
        minValue = minVal
        maxValue = maxVal
        wrapSelectorWheel = true
        value = defaultValue
    }
    private fun getTime():Long{
        return convertTime(hoursPicker.value,minutesPicker.value,secondsPicker.value)
    }
}