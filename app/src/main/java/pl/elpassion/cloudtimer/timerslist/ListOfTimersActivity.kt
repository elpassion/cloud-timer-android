package pl.elpassion.cloudtimer.timerslist

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.TextView
import de.greenrobot.event.EventBus
import pl.elpassion.cloudtimer.*
import pl.elpassion.cloudtimer.domain.Timer
import pl.elpassion.cloudtimer.login.LoginActivity
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
        val newAdapter = ListOfTimersAdapter()
        newAdapter.updateTimers(timers)
        recyclerView.adapter = newAdapter
    }

    private fun startTimerActivity() {
        TimerActivity.start(this, timerActivityResultCode)
    }

    override fun onResume() {
        EventBus.getDefault().register(this)
        handler.postDelayed(TimeRefresher(), oneSec)
        super.onResume()
    }

    override fun onPause() {
        EventBus.getDefault().unregister(this)
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

    fun onEvent(onShareTimerButtonClick: OnShareTimerButtonClick){
        var isLoggedIn = false
        Log.e("ON SHARE BUTTON CLICK", onShareTimerButtonClick.timer.toString())
        if(!isLoggedIn)
            LoginActivity.start(this)
    }

    inner class TimeRefresher : Runnable {
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
    }
}
