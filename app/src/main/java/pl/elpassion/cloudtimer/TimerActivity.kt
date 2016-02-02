package pl.elpassion.cloudtimer

import android.app.Activity
import android.os.Bundle
import android.widget.NumberPicker

/**
 * Created by jasiekpor on 02.02.2016.
 */
class TimerActivity : Activity() {
    val hoursPicker by lazy { findViewById(R.id.hours_picker)as NumberPicker }
    val minutesPicker by lazy { findViewById(R.id.minutes_picker) as NumberPicker }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
        minutesPicker.minValue = 0
        minutesPicker.maxValue = 60
        minutesPicker.wrapSelectorWheel = true
        hoursPicker.minValue = 0
        hoursPicker.maxValue = 24
        hoursPicker.wrapSelectorWheel = true
    }

    override fun onStart() {
        super.onStart()
        minutesPicker.setOnValueChangedListener { numberPicker, i, j -> }
        hoursPicker.setOnValueChangedListener { numberPicker, i, j -> }
    }
}