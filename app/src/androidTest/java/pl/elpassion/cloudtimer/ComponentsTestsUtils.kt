package pl.elpassion.cloudtimer

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewAction
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Matchers.*
import org.hamcrest.core.AllOf.allOf

object ComponentsTestsUtils {

    fun checkTextMatching(id: Int, value: String) {
        onView(withId(id)).check(matches(withText(value)))
    }

    fun checkTextStartsAndEndsWith(id: Int, startText: String, endText : String) {
        onView(withId(id)).check(matches(allOf(withText(startsWith(startText)),
                withText(endsWith(endText)))))
    }

    fun checkTextContainsNoNewLine(id : Int) {
        onView(withId(id)).check(matches(not(withText(containsString("\n")))))
    }

    fun pressButton(id: Int) {
        onView(withId(id)).perform(click());
    }

    fun performAction(id : Int, viewAction : ViewAction) {
        onView(withId(id)).perform(viewAction)
    }
    
    fun typeText(id: Int, text: String) {
        onView(withId(id)).perform(typeText(text))
    }
    
    fun isComponentNotDisplayed(id: Int) {
        onView(withId(id)).check(matches(not(isDisplayed())))
    }

    fun isSnackbarWithTextDisplayed(text: String) {
        onView(withId(android.support.design.R.id.snackbar_text))
                .check(matches(withText(text)));
    }

    fun checkIfComponentHasString(componentId: Int, stringId: Int) {
        onView(withId(componentId)).check(matches(withText(stringId)))
    }
 
    fun isComponentDisplayed(id: Int) {
        onView(withId(id)).check(matches(isDisplayed()))
    }

    fun typeTextInView(id: Int, text: String){
        onView(withId(id)).perform(typeText(text))
    }
}