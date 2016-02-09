package pl.elpassion.cloudtimer

import android.app.PendingIntent.*
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.elpassion.cloudtimer.ComponentsTestsUtils.pressButton
import pl.elpassion.cloudtimer.alarm.AlarmReceiver
import pl.elpassion.cloudtimer.alarm.AlarmReceiver.Companion.REQUEST_CODE
import pl.elpassion.cloudtimer.alarm.NotificationTools
import pl.elpassion.cloudtimer.domain.Timer

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
        pressButton(R.id.start_button)
        val alarmUp = getPendingIntent()
        assertNotNull(alarmUp)
    }

    @Test
    fun alarmSchedulerNoRandomIntentType() {
        clearIntent()
        val requestCodeToTry = 123
        pressButton(R.id.start_button)
        val alarmUp = getPendingIntent(requestCodeToTry)
        assertNull(alarmUp)
    }

    fun alarmReceived() {
        var alarmRecived = false
        NotificationTools.createNotification = { s1: String, s2: String, context: Context ->
            alarmRecived = true
        }
        val newTimer = Timer("test", 1, System.currentTimeMillis() + 1000)

        SystemClock.sleep(100)

        Assert.assertTrue(alarmRecived)
    }

    private fun clearIntent() = getBroadcast(activity, REQUEST_CODE, Intent(activity, alarmReceiverClass), FLAG_CANCEL_CURRENT).cancel()
    private fun getPendingIntent() = getPendingIntent(REQUEST_CODE)
    private fun getPendingIntent(reqCode : Int) = getBroadcast(activity, reqCode, Intent(activity, alarmReceiverClass), FLAG_NO_CREATE)
}
