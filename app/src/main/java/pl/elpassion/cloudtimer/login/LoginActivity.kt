package pl.elpassion.cloudtimer.login

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import de.greenrobot.event.EventBus
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.base.CloudTimerActivity
import pl.elpassion.cloudtimer.timerslist.ListOfTimersActivity

class LoginActivity : CloudTimerActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logging_in)
        LoginHandler.login(intent)
    }

    override fun onNewIntent(intent: Intent) {
        LoginHandler.login(intent)
        super.onNewIntent(intent)
    }

    override fun onStart() {
        EventBus.getDefault().register(this)
        super.onStart()
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    fun onEvent(onLogginSuccess: OnLoginSuccess) {
        if (isTaskRoot)
            ListOfTimersActivity.start(this)

        finish()
    }

    fun onEvent(onLoggingFailure: OnLoggingFailure) {
        val loggingMessage = findViewById(R.id.logging_message) as TextView
        loggingMessage.text = getString(R.string.logging_in_failure)
    }
}