package pl.elpassion.cloudtimer.login

import android.content.Intent
import android.net.Uri
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.elpassion.cloudtimer.ComponentsTestsUtils.isComponentDisplayed
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.login.authtoken.AuthTokenSharedPreferences
import pl.elpassion.cloudtimer.rule
import pl.elpassion.cloudtimer.signin.SignInActivity
import rx.Observable

class LoginActivityFlowBehaviourTests {

    @JvmField @Rule
    val rule = rule<SignInActivity> {
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
        isComponentDisplayed(R.id.group_list_view)

    }

    private fun startLoginActivity() {
        val activity = rule.activity
        val intent = Intent(activity, LoginActivity::class.java)
        intent.data = Uri.parse("test")
        activity.startActivity(intent)
    }

}