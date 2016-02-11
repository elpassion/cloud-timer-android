package pl.elpassion.cloudtimer.timer

import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.NoActivityResumedException
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.elpassion.cloudtimer.TimerDAO
import pl.elpassion.cloudtimer.timerslist.ListOfTimersActivity


@RunWith(AndroidJUnit4::class)
class TimerViewsBackButtonActionWithNoTimersTests {

    companion object {
        private val timerDao by lazy { TimerDAO.getInstance() }
        @BeforeClass
        fun clearDB() {
            timerDao.deleteAll()
        }
    }

    @Rule @JvmField
    val activity = ActivityTestRule<ListOfTimersActivity>(ListOfTimersActivity::class.java)


    @Test(expected = NoActivityResumedException::class)
    fun appWillCloseAfterBackButtonPressedFromTimerView() {
        //when
        pressBack()
        //then exc
    }
}