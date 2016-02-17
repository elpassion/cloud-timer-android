package pl.elpassion.cloudtimer

import android.app.Notification
import android.content.Context
import android.os.SystemClock
import android.support.test.runner.AndroidJUnit4
import android.test.ApplicationTestCase
import org.junit.Assert
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import pl.elpassion.cloudtimer.alarm.NotificationTools
import pl.elpassion.cloudtimer.alarm.scheduleAlarm
import pl.elpassion.cloudtimer.domain.Timer

@RunWith(AndroidJUnit4::class)
class NotificationServiceTest : ApplicationTestCase<CloudTimerApp>(CloudTimerApp::class.java) {
    //@Rule @JvmField
    //val activityRule = ActivityTestRule<TimerActivity>(TimerActivity::class.java)
    private val sampleTimer = Timer("test", 1, System.currentTimeMillis() + 1)

    //todo
    @Ignore
    @Test
    fun alarmReceived() {
        var alarmRecived = false
        NotificationTools.fireNotification = { context: Context, notification: Notification,id : Int ->
            alarmRecived = true
        }
        scheduleAlarm(sampleTimer, application)
        SystemClock.sleep(1000)
        Assert.assertTrue(alarmRecived)
    }

    @Ignore
    @Test
    fun notificationsExists() {
        var myNotificaton : Notification? = null
        NotificationTools.fireNotification = { context: Context, notification: Notification,id : Int  ->
            myNotificaton = notification
        }
        scheduleAlarm(sampleTimer, application)
        SystemClock.sleep(1000)
        Assert.assertNotNull(myNotificaton)
    }
}