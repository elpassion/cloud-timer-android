package pl.elpassion.cloudtimer.login

import android.content.Intent
import android.net.Uri
import org.junit.Rule
import org.junit.Test
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
        loginService = object : LoginService {
            override fun login(email: Login): Observable<User> {
                return Observable.just(User("user", "url", "email", "token"))
            }
        }
        AuthTokenSharedPreferences.sharedPreferences.edit().clear().commit()
    }

    @Test
    fun whenStackOfActivitiesIsEmptyLoginActivityShouldStartListOfTimersActivity() {
        val intent = Intent()
        intent.data = Uri.parse("test")
        rule.launchActivity(intent)
        isComponentDisplayed(R.id.create_new_timer)
    }

}