package pl.elpassion.cloudtimer.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import pl.elpassion.cloudtimer.domain.Timer

fun scheduleAlarm(timer: Timer, context: Context) {
    val intent = AlarmReceiver.create(context, timer)
    val pendingIntent = PendingIntent.getBroadcast(context, AlarmReceiver.REQUEST_CODE,
            intent, PendingIntent.FLAG_UPDATE_CURRENT)
    val alarmTime = timer.endTime
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    if (Build.VERSION.SDK_INT >= 19) {
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent)
    } else {
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent)
    }
}