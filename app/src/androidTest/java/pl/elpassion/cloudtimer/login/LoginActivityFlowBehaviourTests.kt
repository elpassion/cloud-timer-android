package pl.elpassion.cloudtimer.login

import android.content.Intent
import android.net.Uri
import android.support.test.espresso.Espresso.pressBack
import org.junit.After
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
        AuthTokenSharedPreferences.sharedPreferences.edit().clear().commit()
    }

    @After
    fun closePreviousActivities(){
        try {
            (1..6).forEach { pressBack() }
        } catch(e:Exception) { }
    }

    @Test
    fun whenThereIsSignInActivityOnActivityStackGroupActivityShouldBeFired() {
        startLoginActivity()
        isComponentDisplayed(R.id.group_list_view)
    }

    @Test
    fun whenThereIsSignInActivityOnActivityStackGroupActivityShouldBeFiredWhenLoginActivityWasFiredSecondTime() {
        startLoginActivityWithNoData()
        startLoginActivity()
        isComponentDisplayed(R.id.group_list_view)
    }

    @Test
    fun whenUserBackedFromLoginActivitySignInActivityShouldBeResumed() {
        startLoginActivityWithNoData()
        pressBack()
        isComponentDisplayed(R.id.email_input)
    }

    private fun startLoginActivityWithNoData() {
        val intent = Intent(rule.activity, LoginActivity::class.java)
        rule.activity.startActivity(intent)
    }

    private fun startLoginActivity() {
        val activity = rule.activity
        val intent = Intent(activity, LoginActivity::class.java)
        intent.data = Uri.parse("test")
        activity.startActivity(intent)
    }

}