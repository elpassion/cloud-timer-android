package pl.elpassion.cloudtimer

import android.support.test.espresso.ViewAction
import android.support.test.espresso.action.*
import android.util.DisplayMetrics
import org.junit.Rule
import org.junit.Test
import pl.elpassion.cloudtimer.ComponentsTestsUtils.checkTextContainsNoNewLine
import pl.elpassion.cloudtimer.ComponentsTestsUtils.checkTextMatching
import pl.elpassion.cloudtimer.ComponentsTestsUtils.checkTextStartsAndEndsWith
import pl.elpassion.cloudtimer.ComponentsTestsUtils.isComponentDisplayed
import pl.elpassion.cloudtimer.ComponentsTestsUtils.performAction
import pl.elpassion.cloudtimer.ComponentsTestsUtils.typeTextInView

class TimerGUITest {
    @Rule @JvmField
    val rule = rule<TimerActivity> {
        TimerDAO.getInstance().deleteAll()
    }

    val defaultWidth by lazy { getDefaultDisplayMetrics().first / 40 }
    val heightOnRanges by lazy { getDefaultDisplayMetrics().second * (3 / 4) }

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
        val width = defaultWidth * 24
        clickOnTimerWithCoordinates(Pair(width, heightOnRanges))
    }

    private fun clickOnEndRange() {
        val width = defaultWidth * 10
        clickOnTimerWithCoordinates(Pair(width, heightOnRanges))
    }

    private fun clickOnTimerLocation(location: GeneralLocation) {
        performAction(R.id.timer_seekArc, onSeekArcClickWithLocation(location))
    }

    private fun clickOnTimerWithCoordinates(coordinates: Pair<Int, Int>) {
        performAction(R.id.timer_seekArc, onSeekArcClickWithCoordinates(coordinates))
    }

    private fun getExpectedTimerEndTime(): String {
        val duration = rule.activity.timerDuration.text.substring(0, 2).toInt() * 60 * 1000
        val endTime = TimeConverter.formatFromMilliToTime(currentTimeInMillis() + duration)
        return endTime
    }


    @Test
    fun isTimerComponentsDisplayed() {
        isComponentDisplayed(R.id.timer_seekArc)
        isComponentDisplayed(R.id.timer_duration)
        isComponentDisplayed(R.id.timer_time_to_end)
        isComponentDisplayed(R.id.start_timer_button)
    }

    @Test
    fun shouldTimerEndTimeSetProperlyAfterPickTimerDuration() {
        clickOnTimerLocation(GeneralLocation.CENTER_LEFT)
        checkTextMatching(R.id.timer_time_to_end, getExpectedTimerEndTime())
    }

    @Test
    fun shouldAddOneHourAfterCrossHourRange() {
        clickOnEndRange()
        clickOnStartRange()
        checkTextStartsAndEndsWith(R.id.timer_duration, "1:", ":00")
    }

    @Test
    fun isTimerTitleOneLine() {
        typeTextInView(R.id.new_timer_title, "first line \n second line")
        checkTextContainsNoNewLine(R.id.new_timer_title)
    }
}