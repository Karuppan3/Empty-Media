package io.bash_psk.empty_media.domain.resource

import android.app.NotificationManager
import androidx.core.app.NotificationCompat

object ConstantNotification {

    const val EMPTY_MEDIA_CHANNEL_ID = "EMPTY MEDIA"
    const val EMPTY_MEDIA_CHANNEL_NAME = "Empty Media"
    const val EMPTY_MEDIA_CHANNEL_DESCRIPTION = "Show File Operation And Download Information."
    const val EMPTY_MEDIA_CHANNEL_IMPORTANCE = NotificationManager.IMPORTANCE_HIGH

    const val COMMAND_NOTIFICATION_ID = 1
    const val COMMAND_NOTIFICATION_PRIORITY = NotificationCompat.PRIORITY_HIGH

    const val LIBRARY_UPDATE_NOTIFICATION_ID = 2
    const val LIBRARY_UPDATE_NOTIFICATION_PRIORITY = NotificationCompat.PRIORITY_DEFAULT
}