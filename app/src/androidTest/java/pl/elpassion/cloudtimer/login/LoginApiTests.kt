package pl.elpassion.cloudtimer.login

import android.support.test.espresso.Espresso.closeSoftKeyboard
import org.junit.Rule
import org.junit.Test
import pl.elpassion.cloudtimer.ComponentsTestsUtils.isComponentDisplayed
import pl.elpassion.cloudtimer.ComponentsTestsUtils.pressButton
import pl.elpassion.cloudtimer.ComponentsTestsUtils.typeText
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.TimerDAO
import pl.elpassion.cloudtimer.domain.Timer
import pl.elpassion.cloudtimer.network.SignInService
import pl.elpassion.cloudtimer.network.myService
import pl.elpassion.cloudtimer.rule
import rx.Observable

class LoginApiTests {

    @Rule @JvmField
    val rule = rule<LoginActivity>() {
        val alarmDao = TimerDAO.getInstance()
        alarmDao.deleteAll()
        alarmDao.save(Timer("timer", 1000))
    }

    val errorService: SignInService = object : SignInService {
        override fun singIn(email: String): Observable<Any> {
            return Observable.error(Throwable())
        }
    }

    @Test
    fun whenApiReturnErrorCodeViewShouldHaveErrorMessage() {
        myService = errorService
        typeText(R.id.email_input, "email@gmail.com")
        closeSoftKeyboard()
        pressButton(R.id.login_via_email_button)
        isComponentDisplayed(R.id.error_message)
    }

}