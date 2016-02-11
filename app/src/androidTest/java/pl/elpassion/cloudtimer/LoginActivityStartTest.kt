package pl.elpassion.cloudtimer

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.elpassion.cloudtimer.ComponentsTestsUtils.isComponentDisplayed
import pl.elpassion.cloudtimer.ComponentsTestsUtils.pressButton
import pl.elpassion.cloudtimer.domain.Timer
import pl.elpassion.cloudtimer.timerslist.ListOfTimersActivity

@RunWith(AndroidJUnit4::class)
class LoginActivityStartTest {
    @Rule @JvmField
    val rule = ActivityTestRule<ListOfTimersActivity>(ListOfTimersActivity::class.java)

    companion object {
        @BeforeClass @JvmStatic
        fun daoSetup() {
            val alarmDao = TimerDAO.getInstance()
            alarmDao.deleteAll()
            alarmDao.save(Timer("placeholder", 100L))
        }
    }

    @Test
    fun startLoginActivity() {
        pressButton(R.id.timer_share_button)
        isComponentDisplayed(R.id.login_via_email_button)
    }
}