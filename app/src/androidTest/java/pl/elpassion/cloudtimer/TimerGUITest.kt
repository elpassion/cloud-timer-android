package pl.elpassion.cloudtimer

import android.support.test.espresso.Espresso.closeSoftKeyboard
import org.junit.Rule
import org.junit.Test
import pl.elpassion.cloudtimer.ComponentsTestsUtils.isComponentDisplayed

class TimerGUITest {
    @Rule @JvmField
    val rule = rule<TimerActivity> { }

    @Test
    fun fifteenMinutesOnStart() {
        closeSoftKeyboard()
        isComponentDisplayed(R.id.timer_duration)
    }
}