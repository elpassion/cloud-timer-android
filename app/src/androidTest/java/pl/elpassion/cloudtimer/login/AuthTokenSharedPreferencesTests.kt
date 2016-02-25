package pl.elpassion.cloudtimer.login

import org.junit.Assert
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

    @Test
    fun readAuthTokenShouldBeTheSameAsSavedOne() {
        val authToken = "1234"
        AuthTokenSharedPreferences.saveAuthToken(authToken)
        Assert.assertEquals(authToken, AuthTokenSharedPreferences.readAuthToken())
    }

    @Test
    fun ifUserIsNotLoggedInEmailShouldBeNull() {
        assertFalse(AuthTokenSharedPreferences.isLoggedIn())
        assertNull(AuthTokenSharedPreferences.readEmail())
    }

    @Test
    fun ifUserIsLoggedInEmailShouldNotBeNull() {
        AuthTokenSharedPreferences.saveAuthToken("1234")
        assertTrue(AuthTokenSharedPreferences.isLoggedIn())
        assertNotNull(AuthTokenSharedPreferences.readEmail())
    }



}