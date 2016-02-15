package pl.elpassion.cloudtimer.timerslist

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.TimeConverter
import pl.elpassion.cloudtimer.TimerActivity
import pl.elpassion.cloudtimer.TimerDAO
import pl.elpassion.cloudtimer.domain.Timer
import java.util.*

class ListOfTimersActivity : AppCompatActivity() {

    companion object {
        private const val timerActivityResultCode = 1
        private const val oneSec: Long = 1000
        private val handler: Handler = Handler()
    }

    private val createNewTimerButton by lazy { findViewById(R.id.create_new_timer) as FloatingActionButton }
    private val recyclerView by lazy { findViewById(R.id.user_timers_list) as RecyclerView }
    private val timers: MutableList<Timer> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_timers_list_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        loadTimersAndSetUpRecycleView()
        startTimerActivityIfThereIsNoTimers()
        createNewTimerButton.setOnClickListener {
            startTimerActivity()
        }
    }

    private fun loadTimersAndSetUpRecycleView() {
        loadTimersFromDB()
        setUpRecyclerView()
    }

    private fun loadTimersFromDB() {
        val dao = TimerDAO.getInstance()
        timers.clear()
        timers.addAll(dao.findAll())
    }

    private fun startTimerActivityIfThereIsNoTimers() {
        if (timers.isEmpty())
            startTimerActivity()
    }

    private fun setUpRecyclerView() {
        val newAdapter = NewAdapter()
        newAdapter.updateTimers(timers)
        recyclerView.adapter = newAdapter
    }

    private fun startTimerActivity() {
        TimerActivity.start(this, timerActivityResultCode)
    }

    override fun onResume() {
        handler.postDelayed(TimeRefresher(), oneSec)
        super.onResume()
    }

    override fun onPause() {
        handler.removeCallbacks(TimeRefresher())
        super.onPause()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadTimersAndSetUpRecycleView()
        if (isBackedFromTimerActivityAndThereAreNoTimersInDB(requestCode, resultCode))
            finish()
        else
            super.onActivityResult(requestCode, resultCode, data)
    }

    private fun isBackedFromTimerActivityAndThereAreNoTimersInDB(requestCode: Int, resultCode: Int): Boolean {
        return requestCode == timerActivityResultCode && resultCode == RESULT_CANCELED && timers.isEmpty()
    }

    private val adapter : NewAdapter
            get(){return recyclerView.adapter as NewAdapter}

    inner class TimeRefresher : Runnable {
        override fun run() {
            adapter.handleTimersStateChange()
            val notFinishedTimersRange = adapter.getNotFinishedTimersRange()
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val visibleRange = layoutManager.findFirstVisibleItemPosition()..layoutManager.findLastVisibleItemPosition()
            val visibleNotFinishedTimers = notFinishedTimersRange.intersect(visibleRange)
            visibleNotFinishedTimers.forEach {
                val view = layoutManager.findViewByPosition(it)
                val counter = view.findViewById(R.id.timer_counter) as TextView
                val timeLeftInMilliSec = (adapter.adapters[it] as TimerItemAdapter).timer.endTime - System.currentTimeMillis()
                counter.text = TimeConverter.formatFromMilliToMinutes(timeLeftInMilliSec)
            }
            handler.postDelayed(this, oneSec)
        }
    }

}
