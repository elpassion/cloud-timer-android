package pl.elpassion.cloudtimer

import android.support.test.espresso.Espresso.closeSoftKeyboard
import org.junit.Rule
import org.junit.Test
import pl.elpassion.cloudtimer.ComponentsTestsUtils.isComponentDisplayed
import pl.elpassion.cloudtimer.ComponentsTestsUtils.pressButton
import pl.elpassion.cloudtimer.domain.Timer
import pl.elpassion.cloudtimer.timerslist.ListOfTimersActivity

class CreateNewTimerTest {

    @Rule @JvmField
    val rule = rule<ListOfTimersActivity> {
        val timerDao = TimerDAO.getInstance()
        timerDao.deleteAll()
        timerDao.save(Timer(
                title = "TestTimer",
                duration = 35000))
    }

    @Test
    fun isTimerActivityStartsWhenAddNewTimerButtonIsPushed() {
        pressButton(R.id.create_new_timer)
        closeSoftKeyboard()
        isComponentDisplayed(R.id.start_timer_button)
    }

    @Test
    fun shouldTimerTitleSetProperly() {
        ComponentsTestsUtils.pressButton(R.id.create_new_timer)
        ComponentsTestsUtils.typeTextInView(R.id.new_timer_title, "testTimer2")
        closeSoftKeyboard()
        ComponentsTestsUtils.pressButton(R.id.start_timer_button)
        ComponentsTestsUtils.checkTextInRecyclerViewElement(R.id.user_timers_list, "testTimer2")
    }
}