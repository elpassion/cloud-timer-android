package pl.elpassion.cloudtimer.group

import android.content.Context
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
import pl.elpassion.cloudtimer.domain.User
import java.util.*

class NewGroupActivity : CloudTimerActivity() {

    companion object {
        private val timerUUIDKey = "TIMERUUID"
        fun start(context: Context, sharedTimerUUID: String) {
            Log.e("ACTIVITY", " New Group Activity created")
            val intent = Intent(context, NewGroupActivity::class.java)
            intent.putExtra(timerUUIDKey, sharedTimerUUID)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    val timerToolbar by lazy { findViewById(R.id.new_group_toolbar) as Toolbar }
    private val timersRecyclerView by lazy { findViewById(R.id.timers_recycler_view) as RecyclerView }
    private val usersRecyclerView by lazy { findViewById(R.id.users_recycler_view) as RecyclerView }
    val timers: MutableList<Timer> = ArrayList()
    val users: MutableList<User> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_group)
        timerToolbar.inflateMenu(R.menu.new_group_menu)
        timersRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        usersRecyclerView.layoutManager = LinearLayoutManager(this)
        loadRecyclerViews()
    }

    private fun loadRecyclerViews() {
        loadAndSetUpTimersRecycleView()
        loadAndSetUpUsersRecyclerView()
    }

    private fun loadAndSetUpTimersRecycleView() {
        if (intent.extras != null)
            loadSharedTimerFromDB(intent.extras.getString(timerUUIDKey))
        else loadSharedTimerFromDB("test")
        setUpTimersRecyclerView()
    }

    private fun loadSharedTimerFromDB(timerUUID: String) {
        val dao = TimerDAO.getInstance()
        timers.clear()
        timers.add(dao.findOne(timerUUID))
    }

    private fun setUpTimersRecyclerView() {
        val newAdapter = NewGroupListOfTimersAdapter()
        newAdapter.updateTimers(timers)
        timersRecyclerView.adapter = newAdapter
    }

    private fun loadAndSetUpUsersRecyclerView() {
        users.clear()
        loadUsers()
        setUpUsersRecyclerView()
    }

    private fun loadUsers() {
        //todo Download users from server
        users.add(User("Mietek", "mietek@gmail.com"))
    }

    private fun setUpUsersRecyclerView() {
        val newAdapter = NewGroupListOfUsersAdapter()
        newAdapter.updateUsers(users)
        usersRecyclerView.adapter = newAdapter
    }
}