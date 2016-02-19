package pl.elpassion.cloudtimer.login

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import pl.elpassion.cloudtimer.login.authtoken.AuthTokenSharedPreferences

class AuthTokenSharedPreferencesTests {

    @Before()
    fun wipeSharedPreferences() {
        AuthTokenSharedPreferences.sharedPreferences.edit().clear().commit()
    }

    @Test
    fun ifThereIsNoAuthTokenFunctionIsLoggedInShouldReturnFalse() {
        Assert.assertFalse(AuthTokenSharedPreferences.isLoggedIn())
    }

    @Test
    fun afterSavingTokenFunctionIsLoggedInShouldReturnTrue() {
        AuthTokenSharedPreferences.saveAuthToken("1234")
        Assert.assertTrue(AuthTokenSharedPreferences.isLoggedIn())
    }

}