package pl.elpassion.cloudtimer

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.elpassion.cloudtimer.ComponentsTestsUtils.isComponentDisplayed
import pl.elpassion.cloudtimer.ComponentsTestsUtils.pressButton
import pl.elpassion.cloudtimer.timerslist.ListOfTimersActivity

@RunWith(AndroidJUnit4::class)
class CreateNewTimerTest {

    @Rule @JvmField
    val listOfTimersActivity = ActivityTestRule<ListOfTimersActivity>(ListOfTimersActivity::class.java)

    @Test
    fun isTimerActivityStartsWhenAddNewTimerButtonIsPushed() {
        pressButton(R.id.create_new_timer)
        isComponentDisplayed(R.id.start_button)
    }
}