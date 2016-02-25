package pl.elpassion.cloudtimer.login

import android.content.Intent
import android.net.Uri
import android.support.test.espresso.Espresso
import org.junit.After
import org.junit.Rule
import org.junit.Test
import pl.elpassion.cloudtimer.ComponentsTestsUtils
import pl.elpassion.cloudtimer.ComponentsTestsUtils.isComponentDisplayed
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

    @After
    fun closePreviousActivities(){
        try {
            (1..6).forEach { Espresso.pressBack() }
        } catch(e:Exception) { }
    }

    @Test
    fun whenStackOfActivitiesIsEmptyLoginActivityShouldStartListOfTimersActivity() {
        loginService = object : LoginService {
            override fun login(email: Login): Observable<User> {
                return Observable.just(User("user", "url", "email", "token"))
            }
        }
        val intent = Intent()
        intent.data = Uri.parse("test")
        rule.launchActivity(intent)
        isComponentDisplayed(R.id.create_new_timer)
    }

    @Test
    fun whenLoginApiFailedThereShouldBeMessageErrorShowed() {
        loginService = object : LoginService {
            override fun login(email: Login): Observable<User> {
                return Observable.error(Throwable())
            }
        }
        val intent = Intent()
        intent.data = Uri.parse("test")
        rule.launchActivity(intent)
        ComponentsTestsUtils.checkTextMatching(R.id.logging_message, R.string.logging_in_failure)
    }

}