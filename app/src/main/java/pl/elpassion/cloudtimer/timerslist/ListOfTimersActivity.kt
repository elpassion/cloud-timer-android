package pl.elpassion.cloudtimer.timerslist

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import pl.elpassion.cloudtimer.NewTimerActivityV1
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.TimerDAO
import pl.elpassion.cloudtimer.domain.Timer
import java.util.*

class ListOfTimersActivity : AppCompatActivity() {

    private val createNewTimerButton by lazy { findViewById(R.id.create_new_timer) as FloatingActionButton }
    private val recyclerView by lazy { findViewById(R.id.user_timers_list) as RecyclerView }
    private val timers: MutableList<Timer> = ArrayList()
    private val handler: Handler = Handler()
    private val oneSec: Long = 1000
    private val dao = TimerDAO.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_timers_list_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        createNewTimerButton.setOnClickListener {
            startTimerActivity()
        }
    }

    override fun onResume() {
        getTimers()
        startTimerActivityIfThereIsNoTimers()
        recyclerView.adapter = TimersListAdapter(timers)
        handler.postDelayed(TimeRefresher(), oneSec)
        super.onResume()
    }

    private fun getTimers() {
        timers.clear()
        timers.addAll(dao.findAll())
    }

    private fun startTimerActivityIfThereIsNoTimers() {
        if (timers.isEmpty())
            startTimerActivity()
    }

    private fun startTimerActivity() {
//        val intent = Intent(this, TimerActivity::class.java)
        val intent = Intent(this, NewTimerActivityV1::class.java)
        startActivity(intent)
    }

    override fun onPause() {
        handler.removeCallbacks(TimeRefresher())
        super.onPause()
    }

    inner class TimeRefresher : Runnable {
        override fun run() {
            recyclerView.adapter.notifyDataSetChanged()
            handler.postDelayed(this, oneSec)
        }
    }

}
