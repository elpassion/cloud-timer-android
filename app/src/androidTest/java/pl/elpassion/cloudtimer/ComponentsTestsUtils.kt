package pl.elpassion.cloudtimer

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.core.AllOf.allOf

object ComponentsTestsUtils {

    fun checkTextMatching(id: Int, value: String) {
        onView(withId(id)).check(matches(withText(value)))
    }

    fun pressButton(id: Int) {
        onView(withId(id)).perform(ViewActions.click());
    }
    
    fun isComponentDisplayed(id : Int) {
        onView(withId(id)).check(matches(isDisplayed()))
    }

    fun typeTextInView(id: Int, text: String){
        onView(withId(id)).perform(ViewActions.typeText(text))
    }

    fun checkTextInRecyclerViewElement(listId: Int, text: String) {
        onView(withId(listId)).check(matches(hasDescendant(allOf(withId(R.id.timer_title), withText(text)))))
    }
}