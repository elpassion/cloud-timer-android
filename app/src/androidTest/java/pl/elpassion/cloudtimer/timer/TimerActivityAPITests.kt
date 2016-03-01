package pl.elpassion.cloudtimer.timer

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import pl.elpassion.cloudtimer.ComponentsTestsUtils.pressButton
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.TimerDAO
import pl.elpassion.cloudtimer.login.authtoken.AuthTokenSharedPreferences
import pl.elpassion.cloudtimer.rule
import rx.Observable

class TimerActivityAPITests {

    @Rule @JvmField
    val rule = rule<TimerActivity> {
        TimerDAO.getInstance().deleteAll()
        AuthTokenSharedPreferences.saveAuthToken("test")
    }

    var counter = 0

    val neverEndingService = object : SendTimerService {
        override fun sendTimer(timer: TimerToSend): Observable<ReceivedTimer> {
            ++counter
            return Observable.never()
        }
    }
    val successService = object : SendTimerService {
        override fun sendTimer(timer: TimerToSend): Observable<ReceivedTimer> {
            ++counter
            return Observable.just(ReceivedTimer(timer.uuid))
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

    @Test
    fun whenUserIsLoggedInAndServiceReturnSuccessTimerInDBShouldBeMarkedAsSync() {
        sendTimerService = successService
        pressButton(R.id.start_timer_button)
        val timer = TimerDAO.getInstance().findAll().first()
        assertTrue(timer.sync)
    }

    @Test
    fun whenUserIsNotLoggedInServiceShouldNotBeFired() {
        AuthTokenSharedPreferences.sharedPreferences.edit().clear().commit()
        sendTimerService = neverEndingService
        pressButton(R.id.start_timer_button)
        assertEquals(0, counter)
    }


}