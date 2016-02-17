package pl.elpassion.cloudtimer.timer

import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.NoActivityResumedException
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.elpassion.cloudtimer.TimerDAO
import pl.elpassion.cloudtimer.rule
import pl.elpassion.cloudtimer.timerslist.ListOfTimersActivity

@RunWith(AndroidJUnit4::class)
class TimerViewsBackButtonActionWithNoTimersTests {

    @Rule @JvmField
    val rule = rule<ListOfTimersActivity> {
        TimerDAO.getInstance().deleteAll()
    }

    @Test(expected = NoActivityResumedException::class)
    fun appWillCloseAfterBackButtonPressedFromTimerView() {
        pressBack()
    }

}