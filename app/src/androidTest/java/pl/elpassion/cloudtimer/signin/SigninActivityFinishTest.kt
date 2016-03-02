package pl.elpassion.cloudtimer.signin

import android.content.Intent
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.NoActivityResumedException
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.elpassion.cloudtimer.domain.Timer
import pl.elpassion.cloudtimer.login.authtoken.AuthTokenSharedPreferences
import pl.elpassion.cloudtimer.ruleManuallyStarted

class SigninActivityFinishTest {
    @Rule @JvmField
    val rule = ruleManuallyStarted<SignInActivity>() {
        AuthTokenSharedPreferences.saveAuthToken("token")
    }

    @Before
    fun startActivity(){
        val intent = Intent()
        intent.putExtra("timerToShareKey", Timer("31231", 200))
        rule.launchActivity(intent)
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