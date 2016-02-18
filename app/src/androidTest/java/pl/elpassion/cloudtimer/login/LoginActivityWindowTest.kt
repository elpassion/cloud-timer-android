package pl.elpassion.cloudtimer.login

import android.support.test.espresso.Espresso.closeSoftKeyboard
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.elpassion.cloudtimer.ComponentsTestsUtils.isComponentDisplayed
import pl.elpassion.cloudtimer.ComponentsTestsUtils.isComponentNotDisplayed
import pl.elpassion.cloudtimer.ComponentsTestsUtils.pressButton
import pl.elpassion.cloudtimer.ComponentsTestsUtils.typeText
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.network.SignInService
import pl.elpassion.cloudtimer.network.myPointlessService
import pl.elpassion.cloudtimer.rule

@RunWith(AndroidJUnit4::class)
class LoginActivityWindowTest {

    @Rule @JvmField
    val rule = rule<LoginActivity>(){}

    @Test
    fun initTest() {
        isComponentNotDisplayed(R.id.incorrect_login_address)
    }

    @Test
    fun regexNegativeTest() {
        typeText(R.id.email_input, "potato")
        closeSoftKeyboard()
        pressButton(R.id.login_via_email_button)
        isComponentDisplayed(R.id.incorrect_login_address)
    }

    @Test
    fun regexPositiveTest() {
        typeText(R.id.email_input, "potato@gmail.com")
        closeSoftKeyboard()
        pressButton(R.id.login_via_email_button)
        isComponentNotDisplayed(R.id.incorrect_login_address)
    }

    @Test
    fun signInPositiveCaseServiceFired() {
        var isFired = false
        myPointlessService = object : SignInService {
            override fun singIn(email: String) {
                isFired = true
            }
        }
        typeText(R.id.email_input, "potato@gmail.com")
        closeSoftKeyboard()
        pressButton(R.id.login_via_email_button)
        assertTrue(isFired)
    }
}