package pl.elpassion.cloudtimer

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.support.test.runner.AndroidJUnit4
import android.test.ActivityInstrumentationTestCase2
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import pl.elpassion.cloudtimer.ComponentsTestsUtils.pressButton
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class AlarmServiceTest : ActivityInstrumentationTestCase2<MainActivity>(MainActivity::class.java) {

    @Test
    fun alarmSchedulerNoPendingIntentsAtStart()
    {
        activity
        clearIntent()
        val alarmUp = (PendingIntent.getBroadcast
        (activity, AlarmReceiver.REQUEST_CODE,
                Intent(activity, AlarmReceiver::class.java), PendingIntent.FLAG_NO_CREATE))
        Assert.assertTrue(alarmUp == null)
    }

    @Test
    fun alarmSchedulerCreatesPendingIntent()
    {
        activity
        clearIntent()
        pressButton(R.id.start_button)
        val alarmUp = (PendingIntent.getBroadcast
                (activity, AlarmReceiver.REQUEST_CODE,
                        Intent(activity, AlarmReceiver::class.java), PendingIntent.FLAG_NO_CREATE))
        Assert.assertTrue(alarmUp != null)
    }

    @Test
    fun alarmSchedulerNoRandomIntentType()
    {
        activity
        clearIntent()
        val requestCodeToTry = 123
        pressButton(R.id.start_button)
        val alarmUp = (PendingIntent.getBroadcast
        (activity, requestCodeToTry,
                Intent(activity, AlarmReceiver::class.java), PendingIntent.FLAG_NO_CREATE))
        Assert.assertTrue(alarmUp == null)
    }

    fun clearIntent()
    {
        val intent = PendingIntent.getBroadcast(activity, AlarmReceiver.REQUEST_CODE,
                Intent(activity, AlarmReceiver::class.java), PendingIntent.FLAG_CANCEL_CURRENT)
        intent.cancel()
    }

    @Test
    fun alarmReceived()
    {
        var alarmRecived = false
        soundPlayer = { context: Context ->
            alarmRecived = true
        }
        activity

        val newTimer = AlarmData(TimeUnit.MILLISECONDS.convert(1, TimeUnit.SECONDS))
        scheduleAlarm(newTimer, activity)
        //tod

        SystemClock.sleep(2000)

        Assert.assertTrue(alarmRecived)
    }
}