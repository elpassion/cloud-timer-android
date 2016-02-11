package pl.elpassion.cloudtimer

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.elpassion.cloudtimer.ComponentsTestsUtils.isComponentDisplayed
import pl.elpassion.cloudtimer.ComponentsTestsUtils.isComponentNotDisplayed
import pl.elpassion.cloudtimer.ComponentsTestsUtils.pressButton
import pl.elpassion.cloudtimer.ComponentsTestsUtils.typeText

@RunWith(AndroidJUnit4::class)
class LoginActivityWindowTest {
    @Rule @JvmField
    val rule = ActivityTestRule<LoginActivity>(LoginActivity::class.java)

    @Test
    fun initTest() {
        isComponentNotDisplayed(R.id.incorrect_login_address)
    }

    @Test
    fun regexNegativeTest() {
        typeText(R.id.email_input, "potato")
        pressButton(R.id.login_via_email_button)
        isComponentDisplayed(R.id.incorrect_login_address)
    }

    @Test
    fun regexPositiveTest() {
        typeText(R.id.email_input, "potato@gmail.com")
        pressButton(R.id.login_via_email_button)
        isComponentNotDisplayed(R.id.incorrect_login_address)
    }
}