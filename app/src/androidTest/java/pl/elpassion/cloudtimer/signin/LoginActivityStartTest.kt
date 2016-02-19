package pl.elpassion.cloudtimer.signin

import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.elpassion.cloudtimer.ComponentsTestsUtils.isComponentDisplayed
import pl.elpassion.cloudtimer.ComponentsTestsUtils.pressButton
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.TimerDAO
import pl.elpassion.cloudtimer.domain.Timer
import pl.elpassion.cloudtimer.rule
import pl.elpassion.cloudtimer.timerslist.ListOfTimersActivity

@RunWith(AndroidJUnit4::class)
class LoginActivityStartTest {
    @Rule @JvmField
    val rule = rule<ListOfTimersActivity>() {
        val alarmDao = TimerDAO.getInstance()
        alarmDao.deleteAll()
        alarmDao.save(Timer("placeholder", 1000L))
    }

    @Test
    fun startLoginActivity() {
        pressButton(R.id.timer_share_button)
        isComponentDisplayed(R.id.login_via_email_button)
    }
}