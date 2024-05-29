package io.bash_psk.utils.activity

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import io.bash_psk.utils.resource.ConstantString

fun Context.getActivity() : Activity {

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