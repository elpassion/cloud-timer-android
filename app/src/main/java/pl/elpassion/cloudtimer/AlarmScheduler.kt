package pl.elpassion.cloudtimer

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log

public fun scheduleAlarm(timer : AlarmData, context : Context) {
    val intent = Intent(context, AlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(context, AlarmReceiver.REQUEST_CODE,
            intent, PendingIntent.FLAG_UPDATE_CURRENT)
    val alarmTime = System.currentTimeMillis() + timer.timeInMillis
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    alarmManager.setAlarmClock(AlarmManager.AlarmClockInfo(alarmTime, pendingIntent), pendingIntent)
    Log.e("ALARM", "SYS TIME MILIS: "+ System.currentTimeMillis())
}