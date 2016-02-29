package pl.elpassion.cloudtimer.timer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.triggertrap.seekarc.SeekArc
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.TimeConverter.formatFromMilliToMinutes
import pl.elpassion.cloudtimer.TimeConverter.formatFromMilliToTime
import pl.elpassion.cloudtimer.TimerDAO
import pl.elpassion.cloudtimer.alarm.scheduleAlarm
import pl.elpassion.cloudtimer.base.CloudTimerActivity
import pl.elpassion.cloudtimer.domain.Timer
import pl.elpassion.cloudtimer.login.authtoken.AuthTokenSharedPreferences.isLoggedIn

class TimerActivity : CloudTimerActivity() {

    companion object {
        fun start(activity: Activity, timerActivityResultCode: Int) {
            val intent = Intent(activity, TimerActivity::class.java)
            activity.startActivityForResult(intent, timerActivityResultCode)
        }
    }

    val seekArcWrapper by lazy {
        val seekArc = findViewById(R.id.timer_seekArc) as SeekArc
        SeekArcWrapper(seekArc) {
            timerEndTime.text = formatFromMilliToTime(it.endTime)
            timerDuration.text = formatFromMilliToMinutes(it.timerDurationInMillis)
        }
    }
    val timerDuration by lazy { findViewById(R.id.timer_duration) as TextView }
    private val timerEndTime by lazy { findViewById(R.id.timer_time_to_end) as TextView }
    private val startTimerButton by lazy { findViewById(R.id.start_timer_button) as Button }
    private val timerTitle by lazy { findViewById(R.id.new_timer_title) as EditText }
    private val handler = Handler()
    private val timerRefreshRunnable = TimerEndTimeRefresher()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
        timerDuration.text = formatFromMilliToMinutes(seekArcWrapper.timerDurationInMillis)
        refreshTimerEndTime()
        startTimerButton.setOnClickListener {
            startNewTimer()
        }
    }

    private fun refreshTimerEndTime() {
        timerEndTime.text = formatFromMilliToTime(seekArcWrapper.endTime)
    }

    private fun startNewTimer() {
        val newTimer = Timer(timerTitle.text.toString(), seekArcWrapper.timerDurationInMillis)
        scheduleAlarm(newTimer, this)
        TimerDAO.getInstance().save(newTimer)
        if (isLoggedIn())
            sendTimerService.sendTimer(Any()).subscribe(onSendTimerSuccess)
        else
            finish()
    }

    private val onSendTimerSuccess = { user: Any ->
        finish()
    }

    override fun onResume() {
        handler.postDelayed(timerRefreshRunnable, 1000)
        refreshTimerEndTime()
        super.onResume()
    }

    override fun onPause() {
        handler.removeCallbacks(timerRefreshRunnable)
        super.onPause()
    }

    inner class TimerEndTimeRefresher : Runnable {
        override fun run() {
            refreshTimerEndTime()
            handler.postDelayed(this, 1000)
        }
    }
}