package pl.elpassion.cloudtimer.timer

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import pl.elpassion.cloudtimer.ComponentsTestsUtils.pressButton
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.login.authtoken.AuthTokenSharedPreferences
import pl.elpassion.cloudtimer.rule
import rx.Observable

class TimerActivityAPITests {

    @Rule @JvmField
    val rule = rule<TimerActivity> {
        AuthTokenSharedPreferences.saveAuthToken("test")
    }

    var counter = 0

    val neverEndingService = object : SendTimerService {
        override fun sendTimer(timer: Any): Observable<Any> {
            ++counter
            return Observable.never()
        }
    }

    @After
    fun logOut(){
        AuthTokenSharedPreferences.sharedPreferences.edit().clear().commit()
    }

    @Test
    fun serviceShouldBeTriggeredAfterCreatingTimerWhenUserIsLoggedIn() {
        sendTimerService = neverEndingService
        pressButton(R.id.start_timer_button)
        assertEquals(1, counter)
    }

}