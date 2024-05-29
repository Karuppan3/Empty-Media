package io.bash_psk.empty_media.data.repository

import android.annotation.SuppressLint
import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import io.bash_psk.empty_media.domain.repository.EmptyNotification

@SuppressLint("MissingPermission")
class EmptyNotificationImpl(
    private val application: Application
) : EmptyNotification {

    override fun notificationManager(): NotificationManagerCompat {

        return NotificationManagerCompat.from(application)
    }

    override fun createNotificationChannel(
        channelId: String,
        channelName: String,
        channelImportance: Int,
        channelDescription: String
    ) {

        val notificationChannel = NotificationChannel(
            channelId,
            channelName,
            channelImportance
        ).apply {

            description = channelDescription
        }

        notificationManager().createNotificationChannel(notificationChannel)
    }

    override fun notificationBuilder(
        channelId: String
    ) : NotificationCompat.Builder {

        return NotificationCompat.Builder(application, channelId)
    }

    override fun setNotification(
        notificationId: Int,
        notification: Notification
    ) {

        notificationManager().notify(notificationId, notification)
    }

    override fun cancelNotification(notificationId: Int) {

        notificationManager().cancel(notificationId)
    }
}