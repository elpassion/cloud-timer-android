package pl.elpassion.cloudtimer.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.network.myPointlessService

class LoginActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val emailInput by lazy { findViewById(R.id.email_input) as EditText }
    private val loginButton by lazy { findViewById(R.id.login_via_email_button) as Button }
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
            myPointlessService.singIn(input)
        else
            displayError()
    }

    private fun displayError() {
        val label = findViewById(R.id.incorrect_login_address)
        label.visibility = View.VISIBLE
    }
}

