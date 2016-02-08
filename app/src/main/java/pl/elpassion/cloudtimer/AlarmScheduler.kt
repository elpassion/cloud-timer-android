package pl.elpassion.cloudtimer

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import pl.elpassion.cloudtimer.domain.Timer

public fun scheduleAlarm(timer : Timer, context : Context) {
    val intent = Intent(context, AlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(context, AlarmReceiver.REQUEST_CODE,
            intent, PendingIntent.FLAG_UPDATE_CURRENT)
    val alarmTime = System.currentTimeMillis() + timer.duration
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    if (android.os.Build.VERSION.SDK_INT >= 19) {
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent)
    } else {
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent)
    }
    Log.e("ALARM", "SYS TIME MILIS: "+ System.currentTimeMillis())
}