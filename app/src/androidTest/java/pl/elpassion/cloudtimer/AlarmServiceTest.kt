package pl.elpassion.cloudtimer

import android.app.PendingIntent
import android.app.PendingIntent.*
import android.content.Intent
import android.support.test.espresso.Espresso.closeSoftKeyboard
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.elpassion.cloudtimer.ComponentsTestsUtils.pressButton
import pl.elpassion.cloudtimer.alarm.AlarmReceiver
import pl.elpassion.cloudtimer.alarm.AlarmReceiver.Companion.REQUEST_CODE
import pl.elpassion.cloudtimer.login.authtoken.AuthTokenSharedPreferences
import pl.elpassion.cloudtimer.timer.TimerActivity

@RunWith(AndroidJUnit4::class)
class AlarmServiceTest {

    @Rule @JvmField
    val rule = rule<TimerActivity>{
        AuthTokenSharedPreferences.sharedPreferences.edit().clear().commit()
    }
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

    private fun clearIntent() {
        val intent = Intent(rule.activity, alarmReceiverClass)
        getBroadcast(rule.activity, REQUEST_CODE, intent, FLAG_CANCEL_CURRENT).cancel()
    }
    private fun getPendingIntent() = getPendingIntent(REQUEST_CODE)
    private fun getPendingIntent(reqCode: Int): PendingIntent? {
        val intent = Intent(rule.activity, alarmReceiverClass)
        return getBroadcast(rule.activity, reqCode, intent, FLAG_NO_CREATE)
    }
}
