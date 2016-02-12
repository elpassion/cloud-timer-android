package pl.elpassion.cloudtimer.timerslist

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.TimerActivity
import pl.elpassion.cloudtimer.TimerDAO
import pl.elpassion.cloudtimer.adapter.BaseAdapter
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
        loadTimersFromDB()
        startTimerActivityIfThereIsNoTimers()
        setUpRecyclerView()
        createNewTimerButton.setOnClickListener {
            startTimerActivity()
        }
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
        recyclerView.adapter = TimersListAdapter(timers)
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
        loadTimersFromDB()
        if (isBackedFromTimerActivityAndThereAreNoTimersInDB(requestCode, resultCode))
            finish()
        else
            super.onActivityResult(requestCode, resultCode, data)
    }

    private fun isBackedFromTimerActivityAndThereAreNoTimersInDB(requestCode: Int, resultCode: Int): Boolean {
        return requestCode == timerActivityResultCode && resultCode == RESULT_CANCELED && timers.isEmpty()
    }

    inner class TimeRefresher : Runnable {
        override fun run() {
            val adapter = recyclerView.adapter as BaseAdapter
            val adapters = adapter.adapters
            adapters.clear()
            adapters.addAll(createAdaptersForCloudTimerItems(timers))
            recyclerView.adapter.notifyDataSetChanged()
            handler.postDelayed(this, oneSec)
        }
    }

}
