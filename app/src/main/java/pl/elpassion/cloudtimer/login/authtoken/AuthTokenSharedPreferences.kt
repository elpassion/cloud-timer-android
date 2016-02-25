package pl.elpassion.cloudtimer.login.authtoken

import android.content.Context.MODE_PRIVATE
import pl.elpassion.cloudtimer.base.CloudTimerApp.Companion.applicationContext

object AuthTokenSharedPreferences {

    private val sharedPreferencesKey = "pl.elpassion.cloud-timer"
    private val authTokenKey = "authToken"
    val sharedPreferences = applicationContext.getSharedPreferences(sharedPreferencesKey, MODE_PRIVATE)

    fun isLoggedIn(): Boolean = sharedPreferences.contains(authTokenKey)
    fun saveAuthToken(authToken: String) = sharedPreferences.edit().putString(authTokenKey, authToken).commit()

    fun readAuthToken(): String? {
        if (isLoggedIn())
            return sharedPreferences.getString(authTokenKey, "")
        return null
    }

    fun readEmail(): String? {
        if (isLoggedIn())
            return ""
        return null
    }
}