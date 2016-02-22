package pl.elpassion.cloudtimer.timerslist

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.design.widget.Snackbar.LENGTH_INDEFINITE
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import de.greenrobot.event.EventBus
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.TimerActivity
import pl.elpassion.cloudtimer.TimerDAO
import pl.elpassion.cloudtimer.base.CloudTimerActivity
import pl.elpassion.cloudtimer.domain.Timer
import pl.elpassion.cloudtimer.login.LoginHandler
import pl.elpassion.cloudtimer.signin.SignInActivity
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

    private val emailWasSentMessage: String
        get() = getString(R.string.email_was_sent_message)

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
        EventBus.getDefault().register(this)
        super.onResume()
    }

    override fun onPause() {
        timeRefresher.stop()
        EventBus.getDefault().unregister(this)
        super.onPause()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadTimersAndSetUpRecycleView()
        if (isBackedFromTimerActivityAndThereAreNoTimersInDB(requestCode, resultCode))
            finish()
        else if (isBackedFromLoginActivityAndEmailWasSent(requestCode, resultCode))
            Snackbar.make(recyclerView, emailWasSentMessage, LENGTH_INDEFINITE).show()
        else
            super.onActivityResult(requestCode, resultCode, data)
    }

    private fun isBackedFromTimerActivityAndThereAreNoTimersInDB(requestCode: Int, resultCode: Int): Boolean {
        return requestCode == timerActivityResultCode && resultCode == RESULT_CANCELED && timers.isEmpty()
    }

    private fun isBackedFromLoginActivityAndEmailWasSent(requestCode: Int, resultCode: Int): Boolean {
        return requestCode == loginActivityResultCode && resultCode == RESULT_OK
    }

    fun onEvent(onShareTimerButtonClick: OnShareTimerButtonClick) {
        var isLoggedIn = false
        if (!isLoggedIn)
            SignInActivity.start(loginActivityResultCode, this)
    }

    override fun onNewIntent(intent: Intent) {
        if (intent.dataString != null)
            LoginHandler(intent).login()
        super.onNewIntent(intent)
    }
}