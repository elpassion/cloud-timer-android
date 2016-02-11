package pl.elpassion.cloudtimer.timer

import android.support.test.espresso.Espresso.pressBack
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.elpassion.cloudtimer.ComponentsTestsUtils.isComponentDisplayed
import pl.elpassion.cloudtimer.ComponentsTestsUtils.pressButton
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.TimerDAO
import pl.elpassion.cloudtimer.domain.Timer
import pl.elpassion.cloudtimer.timerslist.ListOfTimersActivity


@RunWith(AndroidJUnit4::class)
class TimerViewsBackButtonActionWithTimersTests {

    companion object {
        private val timerDao by lazy { TimerDAO.getInstance() }
        private val oneSec: Long = 1000
        private val timerTitle = "test"
        private fun getTimer(): Timer = Timer(title = timerTitle, duration = oneSec)
        @BeforeClass @JvmStatic
        fun clearDBAndAddOneTimer() {
            timerDao.deleteAll()
            timerDao.save(getTimer())
        }
    }

    @Rule @JvmField
    val activity = ActivityTestRule<ListOfTimersActivity>(ListOfTimersActivity::class.java)

    @Test
    fun timerListActivityWillFireUpAfterBackButtonPressedFromTimerView() {
        //given
        //when
        pressButton(R.id.create_new_timer)
        pressBack()
        //then
        isComponentDisplayed(R.id.create_new_timer)
    }

}