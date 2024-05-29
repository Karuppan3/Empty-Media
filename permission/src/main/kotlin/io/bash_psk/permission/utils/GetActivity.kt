package io.bash_psk.permission.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

internal fun Context.getActivity() : Activity {

    return when (this) {

        is Activity -> {

            this
        }

        is ContextWrapper -> {

            baseContext.getActivity()
        }

        else -> {

            throw NullPointerException("Activity Is Null.")
        }
    }
}