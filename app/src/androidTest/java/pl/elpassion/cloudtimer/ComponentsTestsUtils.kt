package pl.elpassion.cloudtimer

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.Matchers
import org.hamcrest.Matchers.not
import pl.elpassion.cloudtimer.NumberPickerMatcher.Companion.withText

object ComponentsTestsUtils {

    fun checkTextMatching(id: Int, value: String) {
        onView(withId(id)).check(matches(withText(value)))
    }

    fun pressButton(id: Int) {
        onView(withId(id)).perform(ViewActions.click());
    }

    fun typeText(id : Int, text : String) {
        onView(withId(id)).perform(ViewActions.typeText(text))
    }
    
    fun isComponentDisplayed(id : Int) {
        onView(withId(id)).check(matches(isDisplayed()))
    }

    fun isComponentNotDisplayed(id : Int) {
        onView(withId(id)).check(matches(not(isDisplayed())))
    }
}