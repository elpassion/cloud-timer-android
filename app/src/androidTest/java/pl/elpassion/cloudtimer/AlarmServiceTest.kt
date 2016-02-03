package pl.elpassion.cloudtimer

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.elpassion.cloudtimer.ComponentsTestsUtils.pressButton

@RunWith(AndroidJUnit4::class)
class AlarmServiceTest {
    @Rule @JvmField
    public val activity = ActivityTestRule<TimerActivity>(TimerActivity::class.java)

    private val context: Context by lazy { activity.activity }

    @Test
    fun alarmSchedulerNoPendingIntentsAtStart()
    {
        clearIntent()
        val alarmUp = (PendingIntent.getBroadcast
        (context, AlarmReceiver.REQUEST_CODE,
                Intent(context, AlarmReceiver::class.java), PendingIntent.FLAG_NO_CREATE))
        Assert.assertTrue(alarmUp == null)
    }

    @Test
    fun alarmSchedulerCreatesPendingIntent()
    {
        clearIntent()
        pressButton(R.id.start_button)
        val alarmUp = (PendingIntent.getBroadcast
                (context, AlarmReceiver.REQUEST_CODE,
                        Intent(context, AlarmReceiver::class.java), PendingIntent.FLAG_NO_CREATE))
        Assert.assertTrue(alarmUp != null)
    }

    @Test
    fun alarmSchedulerNoRandomIntentType()
    {
        clearIntent()
        val requestCodeToTry = 123
        pressButton(R.id.start_button)
        val alarmUp = (PendingIntent.getBroadcast
        (context, requestCodeToTry,
                Intent(context, AlarmReceiver::class.java), PendingIntent.FLAG_NO_CREATE))
        Assert.assertTrue(alarmUp == null)
    }

    fun clearIntent()
    {
        val intent = PendingIntent.getBroadcast(context, AlarmReceiver.REQUEST_CODE,
                Intent(context, AlarmReceiver::class.java), PendingIntent.FLAG_CANCEL_CURRENT)
        intent.cancel()
    }

    @Test
    fun serviceReaction()
    {
       // val service = AlarmReceiver()
        val i = Intent(context, AlarmReceiver::class.java)
        context!!.sendBroadcast(i)
      //  service.onReceive(context, i)
        // todo
        //Assert.assertTrue()
    }
}