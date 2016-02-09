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
import pl.elpassion.cloudtimer.alarm.AlarmReceiver
import pl.elpassion.cloudtimer.alarm.NotificationReceiver
import pl.elpassion.cloudtimer.alarm.scheduleAlarm
import pl.elpassion.cloudtimer.domain.Timer
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class AlarmServiceTest : ActivityInstrumentationTestCase2<MainActivity>(MainActivity::class.java) {



    @Test
    fun alarmSchedulerNoPendingIntentsAtStart()
    {
        activity
        clearIntent()
        val alarmUp = getPendingIntent()
        Assert.assertTrue(alarmUp == null)
    }

    @Test
    fun alarmSchedulerCreatesPendingIntent()
    {
        activity
        clearIntent()
        pressButton(R.id.start_button)
        val alarmUp = getPendingIntent()
        Assert.assertTrue(alarmUp != null)
    }

    @Test
    fun alarmSchedulerNoRandomIntentType()
    {
        activity
        clearIntent()
        val requestCodeToTry = 123
        pressButton(R.id.start_button)
        val alarmUp = getPendingIntent(requestCodeToTry)
        Assert.assertTrue(alarmUp == null)
    }


    private fun getPendingIntent() : PendingIntent? {
        return getPendingIntent(AlarmReceiver.REQUEST_CODE)
    }

    private fun getPendingIntent(requestCode : Int) : PendingIntent? {
        return (PendingIntent.getBroadcast
        (activity, requestCode,
                Intent(activity, AlarmReceiver::class.java), PendingIntent.FLAG_NO_CREATE))
    }

    private fun clearIntent()
    {
        val intent = PendingIntent.getBroadcast(activity, AlarmReceiver.REQUEST_CODE,
                Intent(activity, AlarmReceiver::class.java), PendingIntent.FLAG_CANCEL_CURRENT)
        intent.cancel()
    }

    @Test
    fun alarmReceived()
    {
        var alarmRecived = false
        NotificationReceiver.displayNotification = { context: Context ->
            alarmRecived = true
        }
        activity

        val newTimer = Timer("test",TimeUnit.MILLISECONDS.convert(1, TimeUnit.MILLISECONDS),System.currentTimeMillis()+1000)
        scheduleAlarm(newTimer, activity)

        SystemClock.sleep(100)

        Assert.assertTrue(alarmRecived)
    }
}