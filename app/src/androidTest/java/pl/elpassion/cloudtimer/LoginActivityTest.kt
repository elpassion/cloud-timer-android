package pl.elpassion.cloudtimer

import android.os.SystemClock
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.test.ActivityInstrumentationTestCase2
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.elpassion.cloudtimer.ComponentsTestsUtils.isComponentDisplayed
import pl.elpassion.cloudtimer.ComponentsTestsUtils.pressButton
import pl.elpassion.cloudtimer.domain.Timer
import pl.elpassion.cloudtimer.timerslist.ListOfTimersActivity

@RunWith(AndroidJUnit4::class)
class LoginActivityTest {
    @Rule @JvmField
    val activity = ActivityTestRule<ListOfTimersActivity>(ListOfTimersActivity::class.java)

    companion object {
        val alarmDao by lazy { TimerDAO.getInstance() }

        @BeforeClass @JvmStatic
        fun a() {
            alarmDao.deleteAll()
            alarmDao.save(Timer("placeholder", 100L))
        }
    }

    @Test
    fun startLoginActivity() {
        pressButton(R.id.timer_share_button)
        SystemClock.sleep(100)
        isComponentDisplayed(R.id.login_via_email_button)
    }
}