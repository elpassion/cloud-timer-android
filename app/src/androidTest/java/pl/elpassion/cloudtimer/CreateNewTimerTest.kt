package pl.elpassion.cloudtimer

import android.support.test.espresso.Espresso.closeSoftKeyboard
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewAction
import android.support.test.espresso.action.*
import android.support.test.espresso.matcher.ViewMatchers.withId
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import pl.elpassion.cloudtimer.ComponentsTestsUtils.checkTextMatching
import pl.elpassion.cloudtimer.ComponentsTestsUtils.checkTimerTitleMatchingOnUserTimers
import pl.elpassion.cloudtimer.ComponentsTestsUtils.isComponentDisplayed
import pl.elpassion.cloudtimer.ComponentsTestsUtils.pressButton
import pl.elpassion.cloudtimer.ComponentsTestsUtils.typeTextInView
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

    private fun onSeekArcClickWithLocation(location: CoordinatesProvider) : ViewAction {
        return GeneralClickAction(Tap.SINGLE, location, Press.FINGER )
    }

    private fun onSeekArcClickWithCoordinates(x : Float, y : Float) : ViewAction {
        return GeneralClickAction(Tap.SINGLE, provideCoordinates(x, y), Press.FINGER)
    }

    private fun provideCoordinates(x : Float, y : Float) : CoordinatesProvider{
        return CoordinatesProvider { view ->
            val screenPositions = IntArray(2)
            view.getLocationOnScreen(screenPositions)
            val screenX = screenPositions[0] + x
            val screenY = screenPositions[1] + y
            floatArrayOf(screenX.toFloat(), screenY.toFloat())
        }
    }

    private fun clickOn55Minutes() {
        onView(withId(R.id.timer_seekArc)).perform(onSeekArcClickWithCoordinates(400f, 300f))
    }

    private fun clickOn7Minutes() {
        onView(withId(R.id.timer_seekArc)).perform(onSeekArcClickWithCoordinates(700f, 300f))
    }

    @Test
    @Ignore
    fun isTimerActivityStartsWhenAddNewTimerButtonIsPushed() {
        pressButton(R.id.create_new_timer)
        closeSoftKeyboard()
        isComponentDisplayed(R.id.start_timer_button)
    }

    @Test
    @Ignore
    fun shouldTimerTitleSetProperly() {
        pressButton(R.id.create_new_timer)
        typeTextInView(R.id.new_timer_title, "testTimer2")
        closeSoftKeyboard()
        pressButton(R.id.start_timer_button)
        checkTimerTitleMatchingOnUserTimers(R.id.user_timers_list, "testTimer2")
    }

    @Test
    @Ignore
    fun shouldTimerEndTimeSetProperlyAfterPickTimerDuration() {
        pressButton(R.id.create_new_timer)
        onView(withId(R.id.timer_seekArc)).perform(onSeekArcClickWithLocation(GeneralLocation.CENTER_LEFT))
        checkTextMatching(R.id.timer_duration, "44:00")
    }

    @Test
    fun shouldAddOneAfterCrossHourRange() {
        pressButton(R.id.create_new_timer)
        closeSoftKeyboard()
        clickOn55Minutes()
        clickOn7Minutes()
        checkTextMatching(R.id.timer_duration, "1:07:00")
    }

    @Test
    fun shouldTimerEndTimeSetProperly() {
        pressButton(R.id.create_new_timer)
        closeSoftKeyboard()
        clickOn7Minutes()
        val currentTimePuls7Minutes = TimeConverter.formatFromMilliToTime(currentTimeInMillis() + 7 * 60 * 1000)
        checkTextMatching(R.id.timer_time_to_end, currentTimePuls7Minutes)
    }
}