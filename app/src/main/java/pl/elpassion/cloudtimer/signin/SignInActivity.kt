package pl.elpassion.cloudtimer.signin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.base.CloudTimerActivity
import pl.elpassion.cloudtimer.common.applySchedulers
import pl.elpassion.cloudtimer.common.emailRegex
import pl.elpassion.cloudtimer.domain.Timer
import pl.elpassion.cloudtimer.groups.GroupActivity
import pl.elpassion.cloudtimer.login.authtoken.AuthTokenSharedPreferences.isLoggedIn

class SignInActivity : CloudTimerActivity() {

    companion object {
        private val timerToShareKey = "timerToShareKey"
        fun start(context: Context, timer: Timer) {
            val intent = Intent(context, SignInActivity::class.java)
            intent.putExtra(timerToShareKey, timer)
            context.startActivity(intent)
        }
    }

    private val emailInput by lazy { findViewById(R.id.email_input) as EditText }
    private val loginButton by lazy { findViewById(R.id.send_activation_email) as Button }
    private val errorMessageTextView by lazy { findViewById(R.id.error_message) as TextView }
    private val timer by lazy { intent.getParcelableExtra<Timer>(timerToShareKey) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        loginButton.setOnClickListener {
            handleInsertedEmail(emailInput.text.toString())
        }
    }

    override fun onResume() {
        if (isLoggedIn()) {
            GroupActivity.start(this)
            finish()
        }
        super.onResume()
    }

    private fun handleInsertedEmail(email: String) {
        if (emailRegex.matches(email))
            signIn(email)
        else
            displayError(incorrectEmail)
    }

    private fun signIn(email: String) {
        loginButton.isEnabled = false
        val signInObject = SignInViaEmail(email)
        signInViaEmailService.singIn(signInObject).applySchedulers().subscribe(onSigninSuccess, onSigninFailure)
    }

    private val onSigninSuccess = { any: Any ->
        loginButton.isEnabled = true
        val emailWasSentMessage = getString(R.string.email_was_sent_message)
        Snackbar.make(emailInput, emailWasSentMessage, Snackbar.LENGTH_INDEFINITE).show()
    }

    private val onSigninFailure = { ex: Throwable ->
        loginButton.isEnabled = true
        displayError(connectionError)
    }

    private val connectionError: String
        get() = this.getString(R.string.connection_error)

    private val incorrectEmail: String
        get() = this.getString(R.string.incorrect_email)

    private fun displayError(errorMessage: String) {
        errorMessageTextView.visibility = View.VISIBLE
        errorMessageTextView.text = errorMessage
    }
}