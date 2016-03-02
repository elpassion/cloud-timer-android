package pl.elpassion.cloudtimer.login

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.base.CloudTimerActivity
import pl.elpassion.cloudtimer.common.applySchedulers
import pl.elpassion.cloudtimer.login.authtoken.AuthTokenSharedPreferences
import pl.elpassion.cloudtimer.timerslist.ListOfTimersActivity

class LoginActivity : CloudTimerActivity() {

    private val retryLoggingInButton by lazy { findViewById(R.id.retry_logging_in) }
    private val loggingMessage by lazy { findViewById(R.id.logging_message) as TextView }
    private val progressbar by lazy { findViewById(R.id.logging_in_progressbar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logging_in)
        loggingMessage.text = getString(R.string.logging_in_message)
        retryLoggingInButton.visibility = GONE
        setUprRetryLoggingInButtonListener()
        login()
    }

    private fun setUprRetryLoggingInButtonListener() {
        retryLoggingInButton.setOnClickListener {
            loggingMessage.text = getString(R.string.logging_in_message)
            progressbar.visibility = VISIBLE
            login()
        }
    }

    fun login() {
        if (intent.dataString != null)
            sendTokenToApi(intent.dataString)
    }

    private fun sendTokenToApi(url: String) {
        val token = url.replace(".*token=".toRegex(), "").replace("&email.*".toRegex(), "")
        loginService.login(Login(token))
                .applySchedulers()
                .subscribe(onLoginSuccess, onLoginFailure)
    }

    override fun onNewIntent(intent: Intent) {
        loggingMessage.text = getString(R.string.logging_in_message)
        retryLoggingInButton.visibility = GONE
        progressbar.visibility = VISIBLE
        this.intent.data = intent.data
        login()
        super.onNewIntent(intent)
    }

    private val onLoginSuccess = { user: User ->
        AuthTokenSharedPreferences.saveAuthToken(user.authToken)
        if (isTaskRoot)
            ListOfTimersActivity.start(this)

        finish()
    }

    private val onLoginFailure = { ex: Throwable ->
        loggingMessage.text = getString(R.string.logging_in_failure)
        retryLoggingInButton.visibility = VISIBLE
        progressbar.visibility = GONE
    }
}