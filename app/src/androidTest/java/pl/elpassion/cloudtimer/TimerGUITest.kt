package pl.elpassion.cloudtimer

import android.support.test.espresso.Espresso.closeSoftKeyboard
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewAction
import android.support.test.espresso.action.*
import android.support.test.espresso.matcher.ViewMatchers
import android.util.DisplayMetrics
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import pl.elpassion.cloudtimer.ComponentsTestsUtils.checkTextMatching
import pl.elpassion.cloudtimer.ComponentsTestsUtils.checkTextStartsAndEndsWith
import pl.elpassion.cloudtimer.ComponentsTestsUtils.isComponentDisplayed
import pl.elpassion.cloudtimer.ComponentsTestsUtils.performAction

class TimerGUITest {
    @Rule @JvmField
    val rule = rule<TimerActivity> {
        TimerDAO.getInstance().deleteAll()
    }

    private fun onSeekArcClickWithLocation(location: CoordinatesProvider): ViewAction {
        return GeneralClickAction(Tap.SINGLE, location, Press.FINGER)
    }

    private fun onSeekArcClickWithCoordinates(coordinates: Pair<Int, Int>): ViewAction {
        return GeneralClickAction(Tap.SINGLE, provideCoordinates(coordinates), Press.FINGER)
    }

    private fun provideCoordinates(coordinates: Pair<Int, Int>): CoordinatesProvider {
        return CoordinatesProvider { view ->
            val screenPositions = IntArray(2)
            view.getLocationOnScreen(screenPositions)
            val screenX = screenPositions[0] + coordinates.first
            val screenY = screenPositions[1] + coordinates.second
            floatArrayOf(screenX.toFloat(), screenY.toFloat())
        }
    }

    private fun getDefaultDisplayMetrics(): Pair<Int, Int> {
        val displayMetrics = DisplayMetrics()
        rule.activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val displayWidth = displayMetrics.widthPixels
        val displayHeight = displayMetrics.heightPixels
        return Pair(displayWidth, displayHeight)
    }

    private fun clickOnStartRange() {
        val width = (getDefaultDisplayMetrics().first / 20) * 12
        val height = getDefaultDisplayMetrics().second * (3 / 4)
        onView(ViewMatchers.withId(R.id.timer_seekArc)).perform(onSeekArcClickWithCoordinates(Pair(width, height)))
    }

    private fun clickOnEndRange() {
        val width = (getDefaultDisplayMetrics().first / 40) * 10
        val height = getDefaultDisplayMetrics().second * (3 / 4)
        onView(ViewMatchers.withId(R.id.timer_seekArc)).perform(onSeekArcClickWithCoordinates(Pair(width, height)))
    }

    private fun clickTimerCenterLeft() {
        performAction(R.id.timer_seekArc, onSeekArcClickWithLocation(GeneralLocation.CENTER_LEFT))
    }


    @Test
    @Ignore
    fun isTimerComponentsDisplayed() {
        closeSoftKeyboard()
        isComponentDisplayed(R.id.timer_seekArc)
        isComponentDisplayed(R.id.timer_duration)
        isComponentDisplayed(R.id.timer_time_to_end)
        isComponentDisplayed(R.id.start_timer_button)
    }

    @Test
    fun shouldTimerEndTimeSetProperlyAfterPickTimerDuration() {
        clickTimerCenterLeft()
        val timerDuration = rule.activity.timerDuration.text.substring(0,2).toInt() * 60 * 1000
        val timerEndTime = TimeConverter.formatFromMilliToTime(currentTimeInMillis() + timerDuration)
        checkTextMatching(R.id.timer_time_to_end, timerEndTime)
    }

    @Test
    @Ignore
    fun shouldAddOneAfterCrossHourRange() {
        closeSoftKeyboard()
        clickOnEndRange()
        clickOnStartRange()
        checkTextStartsAndEndsWith(R.id.timer_duration, "1:", ":00")
    }
}