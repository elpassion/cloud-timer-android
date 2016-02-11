package pl.elpassion.cloudtimer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import pl.elpassion.cloudtimer.network.SignInService
import pl.elpassion.cloudtimer.network.myPointlessService

class LoginActivity : AppCompatActivity() {
    private val input by lazy { findViewById(R.id.email_input) as EditText }
    private val loginBtn by lazy { findViewById(R.id.login_via_email_button) as Button }
    private val regex = Regex("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginBtn.setOnClickListener { view -> validateInput(input.text.toString()) }
    }

    private fun validateInput(input: String) {
        if (!regex.matches(input))
            displayError()
        else
            myPointlessService.singIn(input)
    }

    private fun displayError() {
        val label = findViewById(R.id.incorrect_login_address)
        label.visibility = View.VISIBLE
    }
}

