package pl.elpassion.cloudtimer

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.test.ActivityInstrumentationTestCase2


class SimpleTest : ActivityInstrumentationTestCase2<MainActivity>(MainActivity::class.java){

    override fun setUp() {
        super.setUp()
        activity
    }

    fun testCheckIfHelloWorldIsPresent() {
        onView(withId(R.id.hello_world_text)).check(matches(withText("Hello World!")))
    }

    fun testWhichShouldNotPass() {
        onView(withId(R.id.hello_world_text)).check(matches(withText("Hello !")))
    }
}