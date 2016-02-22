package pl.elpassion.cloudtimer

import android.support.test.rule.ActivityTestRule
import pl.elpassion.cloudtimer.base.CloudTimerActivity

inline fun <reified T : CloudTimerActivity> rule(crossinline beforeActivityFunction: () -> Unit): ActivityTestRule<T> {
    return object : ActivityTestRule<T>(T::class.java) {
        override fun beforeActivityLaunched() = beforeActivityFunction()
    }
}