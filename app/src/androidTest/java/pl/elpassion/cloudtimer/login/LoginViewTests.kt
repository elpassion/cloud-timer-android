package pl.elpassion.cloudtimer.login

import org.junit.Rule
import org.junit.Test
import pl.elpassion.cloudtimer.ComponentsTestsUtils
import pl.elpassion.cloudtimer.ComponentsTestsUtils.checkIfComponentHasString
import pl.elpassion.cloudtimer.ComponentsTestsUtils.isComponentDisplayed
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.rule

class LoginViewTests {


    @Rule @JvmField
    val rule = rule<LoginActivity> {

    }

    @Test
    fun ifCorrectMessageIsPresented() {
        checkIfComponentHasString(R.id.logging_message, R.string.logging_in_message)
    }

    @Test
    fun ifProgressBarIsPresented() {
        isComponentDisplayed(R.id.logging_in_progressbar)
    }

    @Test
    fun ifRetryLoggingInButtonIsNotDisplayed() {
        ComponentsTestsUtils.isComponentNotDisplayed(R.id.retry_logging_in)
    }


}