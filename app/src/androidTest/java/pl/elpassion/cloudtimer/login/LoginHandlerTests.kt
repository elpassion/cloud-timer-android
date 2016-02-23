package pl.elpassion.cloudtimer.login

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import pl.elpassion.cloudtimer.login.authtoken.AuthTokenSharedPreferences

class LoginHandlerTests {

    @Before()
    fun wipeSharedPreferences() {
        AuthTokenSharedPreferences.sharedPreferences.edit().clear().commit()
    }

    @Test
    fun ifThereIsNoAuthTokenFunctionIsLoggedInShouldReturnFalse() {
        Assert.assertFalse(LoginHandler.isLoggedIn())
    }

    @Test
    fun afterSavingTokenFunctionIsLoggedInShouldReturnTrue() {
        AuthTokenSharedPreferences.saveAuthToken("1234")
        Assert.assertTrue(LoginHandler.isLoggedIn())
    }

}