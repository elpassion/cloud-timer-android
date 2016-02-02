package pl.elpassion.cloudtimer

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.elpassion.cloudtimer.PickerComponentsTestsUtils.checkIfHoursPickerHasValue
import pl.elpassion.cloudtimer.PickerComponentsTestsUtils.checkIfMinutesPickerHasValue

@RunWith(AndroidJUnit4::class)
class TimerGUITest {
    @Rule @JvmField
    public val activity = ActivityTestRule<TimerActivity>(TimerActivity::class.java)

    @Test
    fun fifteenMinutesOnStart() {
        checkIfHoursPickerHasValue("0")
        checkIfMinutesPickerHasValue("15")
    }
}