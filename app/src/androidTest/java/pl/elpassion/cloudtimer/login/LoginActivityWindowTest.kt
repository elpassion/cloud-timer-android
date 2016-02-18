package pl.elpassion.cloudtimer.login

import android.support.test.espresso.Espresso.closeSoftKeyboard
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.elpassion.cloudtimer.ComponentsTestsUtils.isComponentDisplayed
import pl.elpassion.cloudtimer.ComponentsTestsUtils.isComponentNotDisplayed
import pl.elpassion.cloudtimer.ComponentsTestsUtils.pressButton
import pl.elpassion.cloudtimer.ComponentsTestsUtils.typeText
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.TimerDAO
import pl.elpassion.cloudtimer.domain.Timer
import pl.elpassion.cloudtimer.rule
import pl.elpassion.cloudtimer.timerslist.ListOfTimersActivity

@RunWith(AndroidJUnit4::class)
class LoginActivityWindowTest {

    @Rule @JvmField
    val rule = rule<ListOfTimersActivity>() {
        val alarmDao = TimerDAO.getInstance()
        alarmDao.deleteAll()
        alarmDao.save(Timer("timer", 10000))
    }

    @Test
    fun initTest() {
        pressButton(R.id.timer_share_button)
        isComponentNotDisplayed(R.id.error_message)
    }

    @Test
    fun regexNegativeTest() {
        sentActivationEmail("potato")
        isComponentDisplayed(R.id.error_message)
    }

    @Test
    fun regexPositiveTest() {
        sentActivationEmail("potato@gmail.com")
        isComponentDisplayed(R.id.create_new_timer)
    }

    private fun sentActivationEmail(email: String) {
        pressButton(R.id.timer_share_button)
        typeText(R.id.email_input, email)
        closeSoftKeyboard()
        pressButton(R.id.login_via_email_button)
    }
}