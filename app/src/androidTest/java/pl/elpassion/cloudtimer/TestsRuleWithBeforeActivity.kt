package pl.elpassion.cloudtimer

import android.support.test.rule.ActivityTestRule
import pl.elpassion.cloudtimer.base.CloudTimerActivity

inline fun <reified T : CloudTimerActivity> rule(crossinline beforeActivityFunction: () -> Unit): ActivityTestRule<T> {
    return object : ActivityTestRule<T>(T::class.java) {
        override fun beforeActivityLaunched() = beforeActivityFunction()
    }
}

inline fun <reified T : CloudTimerActivity> ruleManuallyStarted(crossinline beforeActivityFunction: () -> Unit): ActivityTestRule<T> {
    return object : ActivityTestRule<T>(T::class.java, false, false) {
        override fun beforeActivityLaunched() = beforeActivityFunction()
    }
}