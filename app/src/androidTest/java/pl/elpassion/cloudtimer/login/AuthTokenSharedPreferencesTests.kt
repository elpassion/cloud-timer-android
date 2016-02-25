package pl.elpassion.cloudtimer.login

import org.junit.Assert.*
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
        assertFalse(AuthTokenSharedPreferences.isLoggedIn())
    }

    @Test
    fun afterSavingTokenFunctionIsLoggedInShouldReturnTrue() {
        AuthTokenSharedPreferences.saveAuthToken("1234")
        assertTrue(AuthTokenSharedPreferences.isLoggedIn())
    }

    @Test
    fun ifUserIsNotLoggedInAuthTokenShouldBeNull() {
        assertFalse(AuthTokenSharedPreferences.isLoggedIn())
        assertNull(AuthTokenSharedPreferences.readAuthToken())
    }

    @Test
    fun ifUserIsNotLoggedInAuthTokenShouldNotBeNull() {
        AuthTokenSharedPreferences.saveAuthToken("1234")
        assertTrue(AuthTokenSharedPreferences.isLoggedIn())
        assertNotNull(AuthTokenSharedPreferences.readAuthToken())
    }



}