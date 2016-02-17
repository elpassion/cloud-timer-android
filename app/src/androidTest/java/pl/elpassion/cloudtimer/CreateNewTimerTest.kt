package pl.elpassion.cloudtimer

import android.support.test.espresso.Espresso
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.elpassion.cloudtimer.ComponentsTestsUtils.isComponentDisplayed
import pl.elpassion.cloudtimer.ComponentsTestsUtils.pressButton
import pl.elpassion.cloudtimer.domain.Timer
import pl.elpassion.cloudtimer.timerslist.ListOfTimersActivity

@RunWith(AndroidJUnit4::class)
class CreateNewTimerTest {

    @Rule @JvmField
    val listOfTimersActivity = ActivityTestRule<ListOfTimersActivity>(ListOfTimersActivity::class.java)

    @Before
    fun setUpTimer() {
        val timerDao = TimerDAO.getInstance()
        timerDao.deleteAll()
        timerDao.save(Timer(
                title = "TestTimer",
                duration = 35000))
    }

    @Test
    fun isTimerActivityStartsWhenAddNewTimerButtonIsPushed() {
        pressButton(R.id.create_new_timer)
        Espresso.closeSoftKeyboard()
        isComponentDisplayed(R.id.start_timer_button)
    }
}