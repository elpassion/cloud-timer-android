package pl.elpassion.cloudtimer

import android.support.test.espresso.Espresso
import android.support.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import pl.elpassion.cloudtimer.ComponentsTestsUtils.isComponentDisplayed

class TimerGUITest {
    @Rule @JvmField
    val activity = ActivityTestRule<TimerActivity>(TimerActivity::class.java)

    @Test
    fun fifteenMinutesOnStart() {
        Espresso.closeSoftKeyboard()
        isComponentDisplayed(R.id.timer_duration)
    }
}