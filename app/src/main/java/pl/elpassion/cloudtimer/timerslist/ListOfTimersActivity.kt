package pl.elpassion.cloudtimer.timerslist

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.TimerActivity
import pl.elpassion.cloudtimer.TimerDAO
import pl.elpassion.cloudtimer.base.CloudTimerActivity
import pl.elpassion.cloudtimer.domain.Timer
import java.util.*

class ListOfTimersActivity : CloudTimerActivity() {

    companion object {
        private const val timerActivityResultCode = 1
        private const val loginActivityResultCode = 2
    }

    private val createNewTimerButton by lazy { findViewById(R.id.create_new_timer) as FloatingActionButton }
    private val recyclerView by lazy { findViewById(R.id.user_timers_list) as RecyclerView }
    private val timers: MutableList<Timer> = ArrayList()
    private val timeRefresher = TimeRefresher(this)

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

    private fun setUpRecyclerView() {
        val newAdapter = ListOfTimersAdapter()
        newAdapter.updateTimers(timers)
        recyclerView.adapter = newAdapter
    }

    private fun startTimerActivityIfThereIsNoTimers() {
        if (timers.isEmpty())
            startTimerActivity()
    }

    private fun startTimerActivity() {
        TimerActivity.start(this, timerActivityResultCode)
    }

    override fun onResume() {
        timeRefresher.refreshAfterOneSec()
        super.onResume()
    }

    override fun onPause() {
        timeRefresher.stop()
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

}
