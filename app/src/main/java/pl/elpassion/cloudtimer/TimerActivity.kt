package pl.elpassion.cloudtimer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.Toolbar
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import pl.elpassion.cloudtimer.alarm.scheduleAlarm
import pl.elpassion.cloudtimer.base.CloudTimerActivity
import pl.elpassion.cloudtimer.domain.Timer
import pl.elpassion.cloudtimer.timer.CustomSeekArc
import pl.elpassion.cloudtimer.timer.seekArcChangeListener

class TimerActivity : CloudTimerActivity() {

    companion object {
        fun start(activity: Activity, timerActivityResultCode: Int) {
            val intent = Intent(activity, TimerActivity::class.java)
            activity.startActivityForResult(intent, timerActivityResultCode)
        }
    }

    val customSeekArc by lazy { findViewById(R.id.timer_seekArc) as CustomSeekArc }
    val timerDuration by lazy { findViewById(R.id.timer_duration) as TextView }
    val timerEndTime by lazy { findViewById(R.id.timer_time_to_end) as TextView }
    val startTimerButton by lazy { findViewById(R.id.start_timer_button) as Button }
    val timerTitle by lazy { findViewById(R.id.new_timer_title) as EditText }
    val timerToolbar by lazy { findViewById(R.id.new_timer_toolbar) as Toolbar }
    protected val alarmDao by lazy { TimerDAO.getInstance() }
    private val handler = Handler()
    private val timerRefreshRunnable = TimerEndTimeRefresher()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
        val timerDurationInMillis = customSeekArc.timerDurationInMillis.toLong()
        timerDuration.text = TimeConverter.formatFromMilliToMinutes(timerDurationInMillis)
        setUpSeekArcListener()
        refreshTimerEndTime()
        timerToolbar.inflateMenu(R.menu.timer_menu)
        startTimerButton.setOnClickListener {
            startNewTimer()
        }
    }

    private fun setUpSeekArcListener() {
        customSeekArc.setOnSeekArcChangeListener(seekArcChangeListener {
            val currentTime = currentTimeInMillis()
            val timerEndTimeInMillis = currentTime + customSeekArc.timerDurationInMillis
            timerEndTime.text = TimeConverter.formatFromMilliToTime(timerEndTimeInMillis)
            timerDuration.text = TimeConverter.formatFromMilliToMinutes(customSeekArc.timerDurationInMillis.toLong())
        })
    }

    private fun startNewTimer() {
        val newTimer = Timer(timerTitle.text.toString(), customSeekArc.timerDurationInMillis)
        scheduleAlarm(newTimer, this)
        alarmDao.save(newTimer)
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

    private fun refreshTimerEndTime() {
        val endTime = System.currentTimeMillis() + customSeekArc.timerDurationInMillis
        timerEndTime.text = TimeConverter.formatFromMilliToTime(endTime)
    }

    inner class TimerEndTimeRefresher : Runnable {
        override fun run() {
            refreshTimerEndTime()
            handler.postDelayed(this, 1000)
        }
    }
}