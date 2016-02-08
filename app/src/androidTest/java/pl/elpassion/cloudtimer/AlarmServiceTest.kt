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
import pl.elpassion.cloudtimer.AlarmReceiver.Companion.REQUEST_CODE
import pl.elpassion.cloudtimer.ComponentsTestsUtils.pressButton
import pl.elpassion.cloudtimer.domain.Timer
import java.lang.System.currentTimeMillis

@RunWith(AndroidJUnit4::class)
class AlarmServiceTest {

    @Rule @JvmField
    val activityRule = ActivityTestRule<TimerActivity>(TimerActivity::class.java)
    val activity by lazy { activityRule.activity }
    private val alarmReceiverClass = AlarmReceiver::class.java

    @Test
    fun alarmSchedulerNoPendingIntentsAtStart() {
        clearIntent()
        val alarmUp = getBroadcast(activity, REQUEST_CODE, getIntent(), FLAG_NO_CREATE)
        assertNull(alarmUp)
    }

    @Test
    fun alarmSchedulerCreatesPendingIntent() {
        clearIntent()
        pressButton(R.id.start_button)
        val alarmUp = getBroadcast(activity, REQUEST_CODE, getIntent(), FLAG_NO_CREATE)
        assertNotNull(alarmUp)
    }

    @Test
    fun alarmSchedulerNoRandomIntentType() {
        clearIntent()
        val requestCodeToTry = 123
        pressButton(R.id.start_button)
        val alarmUp = getBroadcast(activity, requestCodeToTry, getIntent(), FLAG_NO_CREATE)
        assertNull(alarmUp)
    }

    fun alarmReceived() {
        var alarmRecived = false
        soundPlayer = { context: Context ->
            alarmRecived = true
        }
        val newTimer = Timer("test", 1, currentTimeMillis() + 1000)
        scheduleAlarm(newTimer, activity)
        //tod

        SystemClock.sleep(100)

        Assert.assertTrue(alarmRecived)
    }

    private fun clearIntent() = getBroadcast(activity, REQUEST_CODE, getIntent(), FLAG_CANCEL_CURRENT).cancel()
    private fun getIntent() = Intent(activity, alarmReceiverClass)
}
