package pl.elpassion.cloudtimer.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.base.CloudTimerActivity
import pl.elpassion.cloudtimer.common.applySchedulers
import pl.elpassion.cloudtimer.login.authtoken.AuthTokenSharedPreferences
import pl.elpassion.cloudtimer.timerslist.ListOfTimersActivity

class LoginActivity : CloudTimerActivity() {

    private val retryLoggingInButton by lazy { findViewById(R.id.retry_logging_in) }
    private val loggingMessage by lazy { findViewById(R.id.logging_message) as TextView }
    private var urlString: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logging_in)
        loggingMessage.text = getString(R.string.logging_in_message)
        setUpRetryLoggingInButton()
        saveUrlFromIntent(intent)
        login()
    }

    private fun setUpRetryLoggingInButton() {
        retryLoggingInButton.visibility = View.GONE
        retryLoggingInButton.setOnClickListener {
            loggingMessage.text = getString(R.string.logging_in_message)
            login()
        }
    }

    fun saveUrlFromIntent(intent: Intent) {
        if (intent.dataString != null)
            urlString = intent.dataString
    }

    override fun onNewIntent(intent: Intent) {
        loggingMessage.text = getString(R.string.logging_in_message)
        setUpRetryLoggingInButton()
        saveUrlFromIntent(intent)
        login()
        super.onNewIntent(intent)
    }

    private fun login() {
        if (urlString != null)
            sendTokenToApi(urlString)
    }

    private fun sendTokenToApi(url: String?) {
        val token = url!!.replace(".*token=".toRegex(), "").replace("&email.*".toRegex(), "")
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
        loggingMessage.text = getString(R.string.logging_in_failure)
        retryLoggingInButton.visibility = View.VISIBLE
    }
}