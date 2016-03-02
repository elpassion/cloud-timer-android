package pl.elpassion.cloudtimer.signin

import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.NoActivityResumedException
import org.junit.After
import org.junit.Rule
import org.junit.Test
import pl.elpassion.cloudtimer.login.authtoken.AuthTokenSharedPreferences
import pl.elpassion.cloudtimer.rule

class SigninActivityFinishTest {
    @Rule @JvmField
    val rule = rule<SignInActivity>() {
        AuthTokenSharedPreferences.saveAuthToken("token")
    }

    @After
    fun clearSharedPreferences(){
        AuthTokenSharedPreferences.sharedPreferences.edit().clear().commit()
    }

    @Test(expected = NoActivityResumedException::class)
    fun whenBackButtonIsPressedFromGroupActivityItShouldCloseApplicationIfItWasStartedFromSignInActivity() {
        pressBack()
    }


}