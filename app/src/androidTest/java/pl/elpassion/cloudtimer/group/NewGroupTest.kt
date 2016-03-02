package pl.elpassion.cloudtimer.group

import android.content.Intent
import android.support.test.espresso.Espresso.closeSoftKeyboard
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.elpassion.cloudtimer.ComponentsTestsUtils.isComponentDisplayed
import pl.elpassion.cloudtimer.ComponentsTestsUtils.isInRecyclerView
import pl.elpassion.cloudtimer.ComponentsTestsUtils.pressButton
import pl.elpassion.cloudtimer.ComponentsTestsUtils.typeTextInView
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.dao.TimerDaoProvider
import pl.elpassion.cloudtimer.domain.Timer
import pl.elpassion.cloudtimer.ruleManuallyStarted

class NewGroupTest {
    @Rule @JvmField
    val rule = ruleManuallyStarted<NewGroupActivity> {
        val timerDAO = TimerDaoProvider.getInstance()
        timerDAO.deleteAll()
        timerDAO.save(Timer("title", duration = 10000, uid = "test"))
    }

    @Before
    fun launchActivityData() {
        val intent = Intent()
        intent.putExtra("Timer", Timer(title = "Title", duration = 50000))
        rule.launchActivity(intent)
    }

    private fun clickOnColorPickerButton() {
        pressButton(R.id.group_colour_settings)
    }

    private fun clickOnAddNewUserButton() {
        pressButton(R.id.add_new_user_to_group_button)
    }

    private fun clickOnAddEmail() {
        pressButton(R.id.add_user_emile_button)
    }

    @Test
    fun componentsShouldBeDisplayed() {
        isComponentDisplayed(R.id.new_group_name)
        isComponentDisplayed(R.id.group_colour_settings)
        isComponentDisplayed(R.id.timers_recycler_view)
        isComponentDisplayed(R.id.users_recycler_view)
        isComponentDisplayed(R.id.create_new_group_button)
        isComponentDisplayed(R.id.add_new_user_to_group_button)
    }

    @Test
    fun shouldTimerBeDisplayedOnTimersList() {
        isInRecyclerView(R.id.timers_recycler_view, R.id.new_group_timer_text_view)
    }

    @Test
    fun afterClickOnColorButtonColorPickerShouldBeDisplayed() {
        clickOnColorPickerButton()
        isComponentDisplayed(R.id.group_color_picker)
    }

    @Test
    fun afterClickOnAddNewUserButtonScreenWithEmailFieldShouldBeDisplayed() {
        clickOnAddNewUserButton()
        closeSoftKeyboard()
        isComponentDisplayed(R.id.enter_emile_layout)
        isComponentDisplayed(R.id.emile_edit_text)
        isComponentDisplayed(R.id.add_user_emile_button)
    }

    @Test
    fun afterAddNewUserToGroupTheUserShouldBeDisplayedAsGroupMember() {
        val email = "newuser@com.pl"
        clickOnAddNewUserButton()
        typeTextInView(R.id.emile_edit_text, email)
        closeSoftKeyboard()
        clickOnAddEmail()
        isInRecyclerView(R.id.users_recycler_view, R.id.user_email_text_view)
    }
}