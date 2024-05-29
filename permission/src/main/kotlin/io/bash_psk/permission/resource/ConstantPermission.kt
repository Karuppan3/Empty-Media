package io.bash_psk.permission.resource

import android.Manifest
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi

internal object ConstantPermission {

    const val READ_STORAGE_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE
    const val WRITE_STORAGE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    const val READ_VIDEO_PERMISSION = Manifest.permission.READ_MEDIA_VIDEO
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    const val POST_NOTIFICATION_PERMISSION = Manifest.permission.POST_NOTIFICATIONS

    const val READ_STORAGE_DESC = "This App Access To Storage."
    const val WRITE_STORAGE_DESC = "This App Perform Read, Copy And Delete Files."
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    const val READ_VIDEO_DESC = "This App Perform Read Videos From Storage."
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    const val POST_NOTIFICATION_DESC = "This App Show Notification."

    const val READ_STORAGE_ACTION = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    const val WRITE_STORAGE_ACTION = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    const val READ_VIDEO_ACTION = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    const val POST_NOTIFICATION_ACTION = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
}