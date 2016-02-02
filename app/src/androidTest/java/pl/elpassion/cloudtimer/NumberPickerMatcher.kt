package pl.elpassion.cloudtimer

import android.support.test.espresso.matcher.BoundedMatcher
import android.view.View
import android.widget.NumberPicker
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.core.IsEqual

class NumberPickerMatcher {
    companion object {
        fun withText(text: String):Matcher<View>{
            return withText(IsEqual<String>(text))
        }
        fun withText(stringMatcher: Matcher<String>): Matcher<View> {
            checkNotNull(stringMatcher)
            return NumberPickerBoundedMatcher(stringMatcher)
        }
    }
}

class NumberPickerBoundedMatcher(val stringMatcher: Matcher<String>) : BoundedMatcher<View, NumberPicker>(NumberPicker::class.java) {
    override fun describeTo(description: Description) {
        description.appendText("with text: ")
        stringMatcher.describeTo(description)
    }

    public override fun matchesSafely(numberPicker: NumberPicker): Boolean {
        return stringMatcher.matches(numberPicker.value.toString())
    }
}