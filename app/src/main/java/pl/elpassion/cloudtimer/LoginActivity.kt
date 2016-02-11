package pl.elpassion.cloudtimer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val input = findViewById(R.id.email_input) as EditText
        val loginBtn = findViewById(R.id.login_via_email_button) as Button
        loginBtn.setOnClickListener { view -> validateInput(input.text.toString()) }
    }

    private fun validateInput(input: String) {
        val regex = Regex("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$")
        if (!regex.matches(input))
            displayError()
    }

    private fun displayError() {
        val label = findViewById(R.id.incorrect_login_address)
        label.visibility = View.VISIBLE
    }
}
