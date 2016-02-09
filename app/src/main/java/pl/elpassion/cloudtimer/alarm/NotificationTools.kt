package pl.elpassion.cloudtimer.alarm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import pl.elpassion.cloudtimer.R

// todo placeholder
object NotificationTools {
    val ALARM_NOTIFICATION_ID = 329515

    fun createNotification(title : String, text : String, context: Context) {
        val builder = createNotificationBuilder(title, text, context)
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(ALARM_NOTIFICATION_ID, builder.build())
    }

    //todo arg for ID and multiple ids?
    fun dismissNotification(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(ALARM_NOTIFICATION_ID)
    }

    private fun createNotificationBuilder(title : String, text : String, context : Context) : NotificationCompat.Builder {
        val mBuilder = NotificationCompat.Builder(context)
        mBuilder.mContentTitle = title
        mBuilder.mContentText = text
        mBuilder.addAction(R.drawable.notification_template_icon_bg, "Dismiss", getDismissIntent(context))
        addSound(mBuilder)
        mBuilder.setSmallIcon(R.drawable.notification_template_icon_bg) //todo placeholder
        return mBuilder
    }

    private fun addSound(builder : NotificationCompat.Builder) {
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        builder.setSound(uri)
    }

    private fun getDismissIntent(context : Context) : PendingIntent {
        val intent = Intent(context, NotificationReceiver::class.java)
        val pi = PendingIntent.getBroadcast(context, 0, intent, 0)
        return pi
    }
}


