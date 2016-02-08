package pl.elpassion.cloudtimer.timerslist

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.TimerActivity
import pl.elpassion.cloudtimer.domain.Group
import pl.elpassion.cloudtimer.domain.Timer

class ListOfTimersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_timers_list_view)
        val timers = listOf<Timer>(getTimer(1), getTimer(2), getTimer(3))
        if (timers.isEmpty()) {
            val intent = Intent(this, TimerActivity::class.java)
            startActivity(intent)
        }
        val recyclerView = findViewById(R.id.user_timers_list) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = TimersListAdapter(timers)
    }

    private fun getTimer(i: Int): Timer {
        return Timer(
                title = "title" + i,
                endTime = 1454961600000,
                duration = 1000 * 60 * 20,
                group = Group("GRUPA" + i)
        )
    }

}
