package io.bash_psk.empty_media.domain.repository

import android.app.Notification
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

interface EmptyNotification {

    fun notificationManager() : NotificationManagerCompat

    fun createNotificationChannel(
        channelId: String,
        channelName: String,
        channelImportance: Int,
        channelDescription: String
    )

    fun notificationBuilder(
        channelId: String
    ) : NotificationCompat.Builder

    fun setNotification(notificationId: Int, notification: Notification)

    fun cancelNotification(notificationId: Int)
}