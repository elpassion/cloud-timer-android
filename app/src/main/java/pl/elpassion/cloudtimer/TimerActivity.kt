package pl.elpassion.cloudtimer

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker

/**
 * Created by jasiekpor on 02.02.2016.
 */
class TimerActivity : Activity() {
    val hoursPicker by lazy { findViewById(R.id.hours_picker)as NumberPicker }
    val minutesPicker by lazy { findViewById(R.id.minutes_picker) as NumberPicker }
    val startButton by lazy { findViewById(R.id.start_button) as Button }
    var countTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
        setNumberPickerValues(hoursPicker, 0, 24)
        setNumberPickerValues(minutesPicker, 0, 60)
    }

    override fun onStart() {
        super.onStart()
        minutesPicker.setOnValueChangedListener { numberPicker, oldVal, newVal -> }
        hoursPicker.setOnValueChangedListener { numberPicker, oldVal, newVal -> }
        startButton.setOnClickListener {
            scheduleAlarm()
        }
    }
    fun calculateTime(){
    }

    private fun setNumberPickerValues(picker: NumberPicker, minVal: Int, maxVal: Int) {
        picker.minValue = minVal
        picker.maxValue = maxVal
        picker.wrapSelectorWheel = true
    }

    public fun scheduleAlarm() {

        val intent = Intent(this, AlarmReceiver::class.java)
        val pIntent = PendingIntent.getBroadcast(this, AlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val firstMillis = System.currentTimeMillis()
        val alarm = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                AlarmManager.INTERVAL_HALF_HOUR, pIntent)
    }
}