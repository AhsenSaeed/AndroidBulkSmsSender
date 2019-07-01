package spartons.com.prosmssenderapp.helper

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import spartons.com.prosmssenderapp.R


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/27/19}
 */

@Suppress("DEPRECATION")
class NotificationHelper constructor(context: Context) : ContextWrapper(context) {

    private val notificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private companion object {
        private const val APPLICATION_CHANNEL = "spartons.com.prosmssenderapp"
        private const val APPLICATION_NOTIFICATION = "Send Sms Notification"
    }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            createChannels()
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun createChannels() {
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val applicationNotification = NotificationChannel(
            APPLICATION_CHANNEL,
            APPLICATION_NOTIFICATION, NotificationManager.IMPORTANCE_HIGH
        )
        applicationNotification.enableLights(true)
        applicationNotification.lightColor = Color.RED
        applicationNotification.setShowBadge(true)
        applicationNotification.setSound(uri, null)
        applicationNotification.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        notificationManager.createNotificationChannel(applicationNotification)
    }

    fun getSmsSendingNotificationBuilder(): Notification.Builder {
        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            Notification.Builder(applicationContext, APPLICATION_CHANNEL)
        else
            Notification.Builder(applicationContext)
        builder.setSmallIcon(R.mipmap.ic_launcher_round)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            builder.setSound(defaultSoundUri)
        }
        return builder.setProgress(100, 0, false)
            .setContentTitle("Sending Bulk Sms")
            .setAutoCancel(false)
    }

    fun notify(notificationBuilder: Notification.Builder, notificationId: Int) {
        notificationManager.notify(notificationId, notificationBuilder.build())
    }
}
