package pl.elpassion.cloudtimer.group

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.TimerDAO
import pl.elpassion.cloudtimer.base.CloudTimerActivity
import pl.elpassion.cloudtimer.domain.Timer
import java.util.*

class NewGroupActivity : CloudTimerActivity() {

    companion object {
        private val timerUUIDKey = "TIMERUUID"
        fun start(activity: Activity, sharedTimerUUID: String) {
            Log.e("ACTIVITY", " New Group Activity created")
            val intent = Intent(activity, NewGroupActivity::class.java)
            intent.putExtra(timerUUIDKey, sharedTimerUUID)
            activity.startActivity(intent)
        }
    }

    val timerToolbar by lazy { findViewById(R.id.new_group_toolbar) as Toolbar }
    private val timersRecyclerView by lazy { findViewById(R.id.timers_recycler_view) as RecyclerView }
    private val usersRecyclerView by lazy { findViewById(R.id.users_recycler_view) as RecyclerView }
    val timers: MutableList<Timer> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_group)
        timerToolbar.inflateMenu(R.menu.new_group_menu)
        timersRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        usersRecyclerView.layoutManager = LinearLayoutManager(this)
        loadTimersAndSetUpRecycleView()
    }

    private fun loadTimersAndSetUpRecycleView() {
        //loadSharedTimerFromDB(intent.extras.getString(timerUUIDKey))
        loadSharedTimerFromDB("test")
        setUpRecyclerView()
    }

    private fun loadSharedTimerFromDB(timerUUID : String) {
        val dao = TimerDAO.getInstance()
        timers.clear()
        timers.add(dao.findOne(timerUUID))
    }

    private fun setUpRecyclerView() {
        val newAdapter = NewGroupListOfTimersAdapter()
        newAdapter.updateTimers(timers)
        timersRecyclerView.adapter = newAdapter
    }
}