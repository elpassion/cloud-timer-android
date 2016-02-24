package pl.elpassion.cloudtimer.alarm

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat

object NotificationTools {

    val idKey = "notificationId"

    fun createAndFireNotification(title: String, text: String, context: Context) {
        val intent = Intent(context, NotificationReceiver::class.java)
        val generatedID = NotificationIdGenerator.incrementAndGet()
        intent.putExtra(idKey, generatedID)
        val pi = PendingIntent.getBroadcast(context, generatedID, intent, 0)
        val notification = createNotification(title, text, context, pi)
        fireNotification(context, notification, generatedID)
    }

    private fun createNotification(title: String, text: String, context: Context, pendingIntent: PendingIntent): Notification {
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        return NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setContentText(text)
                .addAction(R.drawable.notification_template_icon_bg, "Dismiss", pendingIntent)
                .setSound(soundUri)
                .setSmallIcon(R.drawable.notification_template_icon_bg) //todo placeholder
                .build()
    }

    var fireNotification = fun(context: Context, notification: Notification, id: Int) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(id, notification)
    }

    fun dismissNotification(context: Context, intent: Intent) {
        val id = intent.getIntExtra(idKey, -1)
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(id)
    }
}


