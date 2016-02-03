package pl.elpassion.cloudtimer

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import pl.elpassion.cloudtimer.NumberPickerMatcher.Companion.withText

object ComponentsTestsUtils {

    fun checkTextMatching(id: Int, value: String)
    {
        Espresso.onView(withId(id)).check(matches(withText(value)))
    }

    fun pressButton(id: Int)
    {
        Espresso.onView(withId(id)).perform(ViewActions.click());
    }
}