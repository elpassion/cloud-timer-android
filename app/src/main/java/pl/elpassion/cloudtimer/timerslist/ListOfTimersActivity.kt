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
import pl.elpassion.cloudtimer.domain.Group
import pl.elpassion.cloudtimer.domain.Timer
import java.util.*

class ListOfTimersActivity : AppCompatActivity() {

    private val createNewTimerButton by lazy { findViewById(R.id.create_new_timer) as FloatingActionButton }
    private val recyclerView by lazy { findViewById(R.id.user_timers_list) as RecyclerView }
    private val timers: MutableList<Timer> = ArrayList()
    private val fakeTimers = listOf(getTimer(1), getTimer(2), getTimer(3))
    private val handler: Handler = Handler()
    private val oneSec: Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_timers_list_view)
        timers.addAll(fakeTimers)
        if (timers.isEmpty()) {
            startTimerActivity()
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        createNewTimerButton.setOnClickListener {
            startTimerActivity()
        }
    }

    override fun onResume() {
        handler.postDelayed(TimeRefresher(), oneSec)
        super.onResume()
    }

    override fun onPause() {
        handler.removeCallbacks(TimeRefresher())
        super.onPause()
    }

    private fun getTimer(i: Int): Timer {
        return Timer(
                title = "title" + i,
                endTime = 1454961600000 + 86400000,
                duration = 1000 * 60 * 20,
                group = Group("GRUPA" + i)
        )
    }

    private fun startTimerActivity() {
        val intent = Intent(this, TimerActivity::class.java)
        startActivity(intent)
    }

    inner class TimeRefresher : Runnable {
        override fun run() {
            recyclerView.adapter = TimersListAdapter(timers)
            handler.postDelayed(this, oneSec)
        }
    }


}
