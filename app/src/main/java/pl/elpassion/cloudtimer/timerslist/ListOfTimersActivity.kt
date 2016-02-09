package pl.elpassion.cloudtimer.timerslist

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.TimerActivity
import pl.elpassion.cloudtimer.domain.Group
import pl.elpassion.cloudtimer.domain.Timer

class ListOfTimersActivity : AppCompatActivity() {

    val createNewTimerButton by lazy { findViewById(R.id.create_new_timer) as FloatingActionButton }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_timers_list_view)
        val timers = listOf(getTimer(1), getTimer(2), getTimer(3))
        if (timers.isEmpty()) {
            startTimerActivity()
        }
        val recyclerView = findViewById(R.id.user_timers_list) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = TimersListAdapter(timers)

        createNewTimerButton.setOnClickListener {
            startTimerActivity()
        }
    }

    private fun getTimer(i: Int): Timer {
        return Timer(
                title = "title" + i,
                endTime = 1454961600000,
                duration = 1000 * 60 * 20,
                group = Group("GRUPA" + i)
        )
    }

    private fun startTimerActivity() {
        val intent = Intent(this, TimerActivity::class.java)
        startActivity(intent)
    }

}
