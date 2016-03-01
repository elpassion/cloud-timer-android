package pl.elpassion.cloudtimer.timer

import android.support.test.espresso.Espresso.closeSoftKeyboard
import android.support.test.espresso.Espresso.pressBack
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.elpassion.cloudtimer.ComponentsTestsUtils.isComponentDisplayed
import pl.elpassion.cloudtimer.ComponentsTestsUtils.pressButton
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.dao.TimerDaoProvider
import pl.elpassion.cloudtimer.domain.Timer
import pl.elpassion.cloudtimer.rule
import pl.elpassion.cloudtimer.timerslist.ListOfTimersActivity


@RunWith(AndroidJUnit4::class)
class TimerViewsBackButtonActionWithTimersTests {

    private val oneSec: Long = 1000
    private val timerTitle = "test"
    private fun getTimer(): Timer = Timer(timerTitle, oneSec)

    @Rule @JvmField
    val activity = rule<ListOfTimersActivity> {
        val timerDao = TimerDaoProvider.getInstance()
        timerDao.deleteAll()
        timerDao.save(getTimer())
    }

    @Test
    fun timerListActivityWillFireUpAfterBackButtonPressedFromTimerView() {
        pressButton(R.id.create_new_timer)
        closeSoftKeyboard()
        pressBack()
        isComponentDisplayed(R.id.create_new_timer)
    }

}