package io.bash_psk.permission.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi

object SettingsAction {

    fun appSettings(
        activity: Activity
    ) {

        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts(
                "package",
                activity.packageName,
                null
            )
        ).also(activity::startActivity)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun manageStorage(
        activity: Activity
    ) {

        Intent(
            Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
            Uri.fromParts(
                "package",
                activity.packageName,
                null
            )
        ).also(activity::startActivity)
    }

    fun settingAction(
        activity: Activity,
        action: String
    ) {

        Intent(
            action,
            Uri.fromParts(
                "package",
                activity.packageName,
                null
            )
        ).also(activity::startActivity)
    }
}