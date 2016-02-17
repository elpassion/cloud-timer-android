package pl.elpassion.cloudtimer

import android.support.test.espresso.Espresso.closeSoftKeyboard
import org.junit.Rule
import org.junit.Test
import pl.elpassion.cloudtimer.ComponentsTestsUtils.checkTextMatching
import pl.elpassion.cloudtimer.ComponentsTestsUtils.isComponentDisplayed
import pl.elpassion.cloudtimer.ComponentsTestsUtils.typeTextInView

class TimerGUITest {
    @Rule @JvmField
    val rule = rule<TimerActivity> { }

    @Test
    fun fifteenMinutesOnStart() {
        closeSoftKeyboard()
        isComponentDisplayed(R.id.timer_duration)
    }

    @Test
    fun ifTimerIsSetTo25MinutesEndTimeShouldSetProperly() {
        val currentTime = currentTimeInMillis()
        val minutes = 25L*60L*1000L
        typeTextInView(R.id.timer_duration, "25:00")
        checkTextMatching(R.id.timer_time_to_end, (currentTime + minutes).toString())
    }
}