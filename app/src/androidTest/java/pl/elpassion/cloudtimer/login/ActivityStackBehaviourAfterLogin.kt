package pl.elpassion.cloudtimer.login

import android.content.Intent
import android.net.Uri
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.elpassion.cloudtimer.ComponentsTestsUtils.isComponentDisplayed
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.TimerActivity
import pl.elpassion.cloudtimer.TimerDAO
import pl.elpassion.cloudtimer.domain.Timer
import pl.elpassion.cloudtimer.login.authtoken.AuthTokenSharedPreferences
import pl.elpassion.cloudtimer.rule
import rx.Observable

class ActivityStackBehaviourAfterLogin {

    @JvmField @Rule
    val rule = rule<TimerActivity> {
        TimerDAO.getInstance().save(Timer("",200000))
        loginService = object : LoginService {
            override fun login(email: Login): Observable<User> {
                return Observable.just(User("user", "url", "email", "token"))
            }
        }
    }

    @Before
    fun wipeAuthToken() {
        AuthTokenSharedPreferences.sharedPreferences.edit().clear().commit()
    }

    @Test
    fun whenStackOfActivitiesIsEmptyLoginActivityShouldStartListOfTimersActivity() {
        startLoginActivity()
        rule.activity.finish()
        isComponentDisplayed(R.id.create_new_timer)
    }

    @Test
    fun whenThereIsActivityOnActivityStackItShouldBeFiredAfterLoginActivityFinish(){
        startLoginActivity()
        isComponentDisplayed(R.id.new_timer_title)
    }

    private fun startLoginActivity() {
        val activity = rule.activity
        val intent = Intent(activity, LoginActivity::class.java)
        intent.data = Uri.parse("test")
        activity.startActivity(intent)
    }
}