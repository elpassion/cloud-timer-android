package pl.elpassion.cloudtimer.login

import android.content.Intent
import android.util.Log
import de.greenrobot.event.EventBus
import pl.elpassion.cloudtimer.common.applySchedulers
import pl.elpassion.cloudtimer.login.authtoken.AuthTokenSharedPreferences

object LoginHandler {

    fun isLoggedIn(): Boolean = AuthTokenSharedPreferences.isLoggedIn()

    fun login(intent: Intent) {
        if (intent.dataString != null) {
            val token = intent.dataString.replace(".*token=".toRegex(), "")
            loginService.login(Login(token))
                    .applySchedulers()
                    .subscribe(onLoginSuccess, onLoginFailure)
        }
    }

    private val onLoginSuccess = { user: User ->
        AuthTokenSharedPreferences.saveAuthToken(user.authToken)
        EventBus.getDefault().post(OnLoginSuccess())
    }

    private val onLoginFailure = { ex: Throwable ->
        Log.e("Logging in", "exception", ex)
        EventBus.getDefault().post(OnLoggingFailure())
    }
}

class Login(val token: String)
