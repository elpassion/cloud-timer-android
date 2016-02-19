package pl.elpassion.cloudtimer.login

import android.content.Intent
import pl.elpassion.cloudtimer.common.applySchedulers
import pl.elpassion.cloudtimer.login.authtoken.AuthTokenSharedPreferences

class LoginHandler(val intent: Intent) {


    fun login() {
        val token = intent.dataString.replace(".*token=".toRegex(), "")
        loginService.login(Login(token)).applySchedulers().subscribe(onLoginSuccess, onLoginFailure)
    }

    val onLoginSuccess = { user: User ->
        AuthTokenSharedPreferences.saveAuthToken(user.authToken); Unit
    }

    val onLoginFailure = { ex: Throwable ->
    }

}

class Login(val token: String)
