package pl.elpassion.cloudtimer

import android.support.test.rule.ActivityTestRule
import android.support.v7.app.AppCompatActivity

inline fun <reified T : AppCompatActivity> rule(crossinline beforeActivityFunction: () -> Unit): ActivityTestRule<T> {
    return object : ActivityTestRule<T>(T::class.java) {
        override fun beforeActivityLaunched() = beforeActivityFunction()
    }
}