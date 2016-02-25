package pl.elpassion.cloudtimer.login

import android.content.Intent
import android.net.Uri
import android.support.test.espresso.Espresso
import org.junit.After
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import pl.elpassion.cloudtimer.ComponentsTestsUtils.checkTextMatching
import pl.elpassion.cloudtimer.ComponentsTestsUtils.isComponentDisplayed
import pl.elpassion.cloudtimer.ComponentsTestsUtils.pressButton
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.TimerDAO
import pl.elpassion.cloudtimer.domain.Timer
import pl.elpassion.cloudtimer.login.authtoken.AuthTokenSharedPreferences
import pl.elpassion.cloudtimer.ruleManuallyStarted
import rx.Observable

class ActivityStackBehaviourAfterLogin {

    @JvmField @Rule
    val rule = ruleManuallyStarted <LoginActivity> {
        TimerDAO.getInstance().save(Timer("", 100000))
        AuthTokenSharedPreferences.sharedPreferences.edit().clear().commit()
    }

    var serviceCallCounter  = 0

    val loginServiceSuccess: LoginService = object : LoginService {
        override fun login(email: Login): Observable<User> {
            ++serviceCallCounter
            return Observable.just(User("user", "url", "email", "token"))
        }
    }

    val loginServiceFail: LoginService = object : LoginService {
        override fun login(email: Login): Observable<User> {
            ++serviceCallCounter
            return Observable.error(Throwable())
        }
    }

    @After
    fun closePreviousActivities(){
        try {
            (1..6).forEach { Espresso.pressBack() }
        } catch(e:Exception) { }
    }

    @Test
    fun whenStackOfActivitiesIsEmptyLoginActivityShouldStartListOfTimersActivity() {
        loginService = loginServiceSuccess
        launchActivityWithData()
        isComponentDisplayed(R.id.create_new_timer)
    }

    @Test
    fun whenLoginApiFailedThereShouldBeMessageErrorAndRetryLoggingInButtonShowed() {
        loginService = loginServiceFail
        launchActivityWithData()
        checkTextMatching(R.id.logging_message, R.string.logging_in_failure)
        isComponentDisplayed(R.id.retry_logging_in)
    }

    @Test
    fun ifUserHasTriedLogInAgainThereShouldBeTwoCallsToApi() {
        loginService = loginServiceFail
        launchActivityWithData()
        loginService = loginServiceSuccess
        pressButton(R.id.retry_logging_in)
        Assert.assertEquals(2, serviceCallCounter)
    }

    private fun launchActivityWithData() {
        val intent = Intent()
        intent.data = Uri.parse("test")
        rule.launchActivity(intent)
    }


}