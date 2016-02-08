package pl.elpassion.cloudtimer.alarm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.util.Log
import pl.elpassion.cloudtimer.R

//placeholder:
val ALARM_NOTIFICATION_ID = 329515

fun createNotificationManager(title : String, text : String, context: Context) {
    val builder = createNotificationBuilder(title, text, context)
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.notify(ALARM_NOTIFICATION_ID, builder.build())
}

private fun createNotificationBuilder(title : String, text : String, context : Context) : NotificationCompat.Builder {
    val mBuilder = NotificationCompat.Builder(context)
    mBuilder.mContentTitle = title
    mBuilder.mContentText = text
    mBuilder.addAction(R.drawable.notification_template_icon_bg, "Dismiss", getDismissIntent(context))
    //mBuilder.setContentIntent(getDismissIntent(context))
    Log.e("CATLOG", "intent created")
    //todo placeholder v
    mBuilder.setSmallIcon(R.drawable.notification_template_icon_bg)
    return mBuilder
}

private fun getDismissIntent(context : Context) : PendingIntent {
    // todo INTENT NOTIFICATION BUILDER
    val intent = Intent(context, NotificationReceiver::class.java)
    val pi = PendingIntent.getActivity(context, ALARM_NOTIFICATION_ID, intent, 0)
    return pi
}

