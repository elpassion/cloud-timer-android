package pl.elpassion.cloudtimer.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.network.myService

class LoginActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val emailInput by lazy { findViewById(R.id.email_input) as EditText }
    private val loginButton by lazy { findViewById(R.id.login_via_email_button) as Button }
    val errorMessageTextView by lazy { findViewById(R.id.error_message) as TextView }
    private val regex = Regex("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginButton.setOnClickListener {
            validateEmail(emailInput.text.toString())
        }
    }

    private fun validateEmail(input: String) {
        if (regex.matches(input))
            myService.singIn(input).subscribe({},{displayError(connectionError)})
        else
            displayError(incorrectEmail)
    }

    private val connectionError :String
            get() = this.getString(R.string.connection_error)

    private val incorrectEmail :String
            get() = this.getString(R.string.incorrect_email)

    private fun displayError(errorMessage : String) {
        errorMessageTextView.visibility = View.VISIBLE
        errorMessageTextView.text = errorMessage
    }
}

