package pl.elpassion.cloudtimer.login

import android.support.test.espresso.Espresso.closeSoftKeyboard
import org.junit.Rule
import org.junit.Test
import pl.elpassion.cloudtimer.CloudTimerApp.Companion.applicationContext
import pl.elpassion.cloudtimer.ComponentsTestsUtils.checkTextMatching
import pl.elpassion.cloudtimer.ComponentsTestsUtils.isSnackbarWithTextDisplayed
import pl.elpassion.cloudtimer.ComponentsTestsUtils.pressButton
import pl.elpassion.cloudtimer.ComponentsTestsUtils.typeText
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.TimerDAO
import pl.elpassion.cloudtimer.domain.Timer
import pl.elpassion.cloudtimer.network.SignInService
import pl.elpassion.cloudtimer.network.myService
import pl.elpassion.cloudtimer.rule
import pl.elpassion.cloudtimer.timerslist.ListOfTimersActivity
import rx.Observable

class LoginApiTests {

    @Rule @JvmField
    val rule = rule<ListOfTimersActivity>() {
        val alarmDao = TimerDAO.getInstance()
        alarmDao.deleteAll()
        alarmDao.save(Timer("timer", 1000))
    }

    val errorService: SignInService = object : SignInService {
        override fun singIn(email: String): Observable<Any> {
            return Observable.error(Throwable())
        }
    }

    val successService: SignInService = object : SignInService {
        override fun singIn(email: String): Observable<Any> {
            return Observable.just(Any())
        }
    }

    @Test
    fun whenApiReturnErrorCodeViewShouldHaveErrorMessage() {
        myService = errorService
        sendActivationLink()
        val connectionErrorMessage = applicationContext.getString(R.string.connection_error)
        checkTextMatching(R.id.error_message, connectionErrorMessage)
    }

    @Test
    fun whenApiReturnSuccessCodeAppShouldReturnToListOfTimersActivityAndShowToastWithMessage() {
        myService = successService
        sendActivationLink()
        val emailWasSentMessage = applicationContext.getString(R.string.email_was_sent_message)
        isSnackbarWithTextDisplayed(emailWasSentMessage)
    }

    private fun sendActivationLink() {
        pressButton(R.id.timer_share_button)
        typeText(R.id.email_input, "email@gmail.com")
        closeSoftKeyboard()
        pressButton(R.id.login_via_email_button)
    }

}