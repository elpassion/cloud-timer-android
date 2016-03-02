package pl.elpassion.cloudtimer.signin

import android.support.test.espresso.Espresso.closeSoftKeyboard
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.elpassion.cloudtimer.ComponentsTestsUtils.checkIfComponentHasString
import pl.elpassion.cloudtimer.ComponentsTestsUtils.isComponentDisabled
import pl.elpassion.cloudtimer.ComponentsTestsUtils.isComponentEnabled
import pl.elpassion.cloudtimer.ComponentsTestsUtils.isComponentNotDisplayed
import pl.elpassion.cloudtimer.ComponentsTestsUtils.pressButton
import pl.elpassion.cloudtimer.ComponentsTestsUtils.typeText
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.dao.TimerDaoProvider
import pl.elpassion.cloudtimer.domain.Timer
import pl.elpassion.cloudtimer.login.authtoken.AuthTokenSharedPreferences
import pl.elpassion.cloudtimer.rule
import rx.Observable

@RunWith(AndroidJUnit4::class)
class SigninActivityWindowTest {

    @Rule @JvmField
    val rule = rule<SignInActivity>() {
        val alarmDao = TimerDaoProvider.getInstance()
        alarmDao.deleteAll()
        alarmDao.save(Timer("timer", 10000))
        AuthTokenSharedPreferences.sharedPreferences.edit().clear().commit()
    }

    @Test
    fun initTest() {
        isComponentNotDisplayed(R.id.error_message)
    }

    @Test
    fun regexNegativeTest() {
        sentActivationEmail("potato")
        checkIfComponentHasString(R.id.error_message, R.string.incorrect_email)
    }

    @Test
    fun regexPositiveTest() {
        var isCorrectMail = false
        signInViaEmailService = object : SignInViaEmailService {
            override fun singIn(email: SignInViaEmail): Observable<Any> {
                isCorrectMail = true
                return Observable.error(Throwable())
            }
        }
        sentActivationEmail("potato@gmail.com")
        assertTrue(isCorrectMail)
    }


    @Test
    fun whenEmailWasSentButtonSendShouldBeDisabled() {
        signInViaEmailService = object : SignInViaEmailService {
            override fun singIn(email: SignInViaEmail): Observable<Any> {
                return Observable.never()
            }
        }
        sentActivationEmail("potato@gmail.com")
        isComponentDisabled(R.id.send_activation_email)
    }

    @Test
    fun whenIncorrectEmailWasInsertedSendButtonShouldBeEnabled() {
        sentActivationEmail("potato")
        isComponentEnabled(R.id.send_activation_email)
    }

    private fun sentActivationEmail(email: String) {
        typeText(R.id.email_input, email)
        closeSoftKeyboard()
        pressButton(R.id.send_activation_email)
    }
}