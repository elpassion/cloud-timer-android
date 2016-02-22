package pl.elpassion.cloudtimer

import android.app.PendingIntent.*
import android.content.Intent
import android.support.test.espresso.Espresso.closeSoftKeyboard
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.elpassion.cloudtimer.ComponentsTestsUtils.pressButton
import pl.elpassion.cloudtimer.alarm.AlarmReceiver
import pl.elpassion.cloudtimer.alarm.AlarmReceiver.Companion.REQUEST_CODE

@RunWith(AndroidJUnit4::class)
class AlarmServiceTest {

    @Rule @JvmField
    val activityRule = ActivityTestRule<TimerActivity>(TimerActivity::class.java)
    val activity by lazy { activityRule.activity }
    private val alarmReceiverClass = AlarmReceiver::class.java

    @Test
    fun alarmSchedulerNoPendingIntentsAtStart() {
        clearIntent()
        val alarmUp = getPendingIntent()
        assertNull(alarmUp)
    }

    @Test
    fun alarmSchedulerCreatesPendingIntent() {
        clearIntent()
        closeSoftKeyboard()
        pressButton(R.id.start_timer_button)
        val alarmUp = getPendingIntent()
        assertNotNull(alarmUp)
    }

    @Test
    fun alarmSchedulerNoRandomIntentType() {
        clearIntent()
        val requestCodeToTry = 123
        closeSoftKeyboard()
        pressButton(R.id.start_timer_button)
        val alarmUp = getPendingIntent(requestCodeToTry)
        assertNull(alarmUp)
    }

    private fun clearIntent() = getBroadcast(activity, REQUEST_CODE, Intent(activity, alarmReceiverClass), FLAG_CANCEL_CURRENT).cancel()
    private fun getPendingIntent() = getPendingIntent(REQUEST_CODE)
    private fun getPendingIntent(reqCode : Int) = getBroadcast(activity, reqCode, Intent(activity, alarmReceiverClass), FLAG_NO_CREATE)
}
