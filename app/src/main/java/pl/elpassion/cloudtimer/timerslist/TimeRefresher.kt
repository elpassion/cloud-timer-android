package pl.elpassion.cloudtimer.timerslist

import android.app.Activity
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.TimeConverter
import pl.elpassion.cloudtimer.currentTimeInMillis

class TimeRefresher(val activity: Activity) : Runnable {
    private val handler: Handler = Handler()
    private val oneSec: Long = 1000
    private val recyclerView by lazy { activity.findViewById(R.id.user_timers_list) as RecyclerView }

    override fun run() {
        handleCounterRefresh()
        adapter.handleTimersStateChange()
        handler.postDelayed(this, oneSec)
    }

    private fun handleCounterRefresh() {
        val notFinishedTimersRange = adapter.getNotFinishedTimersRange()
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val visibleRange = layoutManager.findFirstVisibleItemPosition()..layoutManager.findLastVisibleItemPosition()
        val visibleNotFinishedTimers = notFinishedTimersRange.intersect(visibleRange)
        foreachNotFinishedTimerRefreshCounter(layoutManager, visibleNotFinishedTimers)
    }

    private fun foreachNotFinishedTimerRefreshCounter(layoutManager: LinearLayoutManager, visibleNotFinishedTimers: Set<Int>) {
        visibleNotFinishedTimers.forEach {
            val view = layoutManager.findViewByPosition(it)
            val counter = view.findViewById(R.id.timer_counter) as TextView
            val timerAdapter = adapter.adapters[it] as TimerItemAdapter
            val timeLeftInMilliSec = timerAdapter.timer.endTime - currentTimeInMillis()
            counter.text = TimeConverter.formatFromMilliToMinutes(timeLeftInMilliSec)
        }
    }

    val adapter: ListOfTimersAdapter
        get() = recyclerView.adapter as ListOfTimersAdapter

    fun refreshAfterOneSec() = handler.postDelayed(this, oneSec)
    fun stop() = handler.removeCallbacks(this)

}