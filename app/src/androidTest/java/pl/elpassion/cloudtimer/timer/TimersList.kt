package pl.elpassion.cloudtimer.timer

import android.support.test.espresso.Espresso.closeSoftKeyboard
import org.junit.Rule
import org.junit.Test
import pl.elpassion.cloudtimer.ComponentsTestsUtils.isComponentDisplayed
import pl.elpassion.cloudtimer.ComponentsTestsUtils.pressButton
import pl.elpassion.cloudtimer.ComponentsTestsUtils.typeTextInView
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.TimerDAO
import pl.elpassion.cloudtimer.rule
import pl.elpassion.cloudtimer.timerslist.ListOfTimersActivity

class TimersList {

    @Rule @JvmField
    val rule = rule<ListOfTimersActivity> {
        TimerDAO.getInstance().deleteAll()
    }

    @Test
    fun addedTimerShouldBeOnTimersList() {
        typeTextInView(R.id.timer_title, "test")
        closeSoftKeyboard()
        pressButton(R.id.start_button)
        isComponentDisplayed(R.id.timer_share_button)
    }

}