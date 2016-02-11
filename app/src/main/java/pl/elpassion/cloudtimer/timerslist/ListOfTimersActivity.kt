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
        setUpRecyclerView()
        recyclerView.layoutManager = LinearLayoutManager(this)
        createNewTimerButton.setOnClickListener {
            startTimerActivity()
        }
    }

    private fun setUpRecyclerView() {
        getTimers()
        startTimerActivityIfThereIsNoTimers()
        recyclerView.adapter = TimersListAdapter(timers)
    }

    override fun onResume() {
        getTimers()
        handler.postDelayed(TimeRefresher(), oneSec)
        super.onResume()
    }

    private fun getTimers() {
        val dao = TimerDAO.getInstance()
        timers.clear()
        timers.addAll(dao.findAll())
    }

    private fun startTimerActivityIfThereIsNoTimers() {
        if (timers.isEmpty())
            startTimerActivity()
    }

    private fun startTimerActivity() {
        TimerActivity.start(this, timerActivityResultCode)
    }

    override fun onPause() {
        handler.removeCallbacks(TimeRefresher())
        super.onPause()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
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
            recyclerView.adapter.notifyDataSetChanged()
            handler.postDelayed(this, oneSec)
        }
    }

}
