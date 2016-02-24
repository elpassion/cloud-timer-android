package pl.elpassion.cloudtimer.login

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.base.CloudTimerActivity
import pl.elpassion.cloudtimer.common.applySchedulers
import pl.elpassion.cloudtimer.login.authtoken.AuthTokenSharedPreferences
import pl.elpassion.cloudtimer.timerslist.ListOfTimersActivity

class LoginActivity : CloudTimerActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logging_in)
        login(intent)
    }

    override fun onNewIntent(intent: Intent) {
        login(intent)
        super.onNewIntent(intent)
    }

    private fun login(intent: Intent) {
        if (intent.dataString != null)
            sendTokenToApi(intent.dataString)
    }

    private fun sendTokenToApi(url: String) {
        val token = url.replace(".*token=".toRegex(), "").replace("&email.*".toRegex(), "")
        loginService.login(Login(token))
                .applySchedulers()
                .subscribe(onLoginSuccess, onLoginFailure)
    }

    private val onLoginSuccess = { user: User ->
        AuthTokenSharedPreferences.saveAuthToken(user.authToken)
        if (isTaskRoot)
            ListOfTimersActivity.start(this)

        finish()
    }

    private val onLoginFailure = { ex: Throwable ->
        val loggingMessage = findViewById(R.id.logging_message) as TextView
        loggingMessage.text = getString(R.string.logging_in_failure)
    }

}