package pl.elpassion.cloudtimer

import android.support.test.espresso.Espresso
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import pl.elpassion.cloudtimer.NumberPickerMatcher.Companion.withText

object PickerComponentsTestsUtils {

    fun checkIfHoursPickerHasValue(value: String) {
        checkIfNumberPickerHasValue(R.id.hours_picker, value)
    }

    fun checkIfMinutesPickerHasValue(value: String) {
        checkIfNumberPickerHasValue(R.id.minutes_picker, value)
    }

    fun checkIfNumberPickerHasValue(id: Int, value: String) {
        Espresso.onView(withId(id)).check(matches(withText(value)))
    }
}