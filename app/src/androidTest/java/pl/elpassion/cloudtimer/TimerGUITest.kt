package pl.elpassion.cloudtimer

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.elpassion.cloudtimer.ComponentsTestsUtils.checkTextMatching

@RunWith(AndroidJUnit4::class)
class TimerGUITest {
    @Rule @JvmField
    public val activity = ActivityTestRule<TimerActivity>(TimerActivity::class.java)

    @Test
    fun fifteenMinutesOnStart() {
        checkTextMatching(R.id.hours_picker, "0")
        checkTextMatching(R.id.minutes_picker, "15")
        checkTextMatching(R.id.seconds_picker,"0")
    }
}