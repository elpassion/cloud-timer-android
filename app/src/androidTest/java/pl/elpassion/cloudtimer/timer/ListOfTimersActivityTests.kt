package pl.elpassion.cloudtimer.timer

import org.junit.Rule
import org.junit.Test
import pl.elpassion.cloudtimer.ComponentsTestsUtils.isComponentDisplayed
import pl.elpassion.cloudtimer.ComponentsTestsUtils.pressButton
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.TimerDAO
import pl.elpassion.cloudtimer.currentTimeInMillis
import pl.elpassion.cloudtimer.domain.Timer
import pl.elpassion.cloudtimer.login.authtoken.AuthTokenSharedPreferences
import pl.elpassion.cloudtimer.rule
import pl.elpassion.cloudtimer.timerslist.ListOfTimersActivity

class ListOfTimersActivityTests {

    @Rule @JvmField
    val rule = rule<ListOfTimersActivity> {
        currentTimeInMillis = { 0 }
        TimerDAO.getInstance().deleteAll()
        TimerDAO.getInstance().save(Timer("test", 1))
    }

    @Test
    fun groupActivityShouldBeStartedWhenUserIsLoggedInOnShareButtonCLick() {
        AuthTokenSharedPreferences.saveAuthToken("token")
        pressButton(R.id.timer_share_button)
        isComponentDisplayed(R.id.group_list_view)
    }

    @Test
    fun signinActivityShouldBeStartedWhenUserIsNotLoggedInOnShareButtonCLick() {
        AuthTokenSharedPreferences.sharedPreferences.edit().clear().commit()
        pressButton(R.id.timer_share_button)
        isComponentDisplayed(R.id.send_activation_email)
    }
}