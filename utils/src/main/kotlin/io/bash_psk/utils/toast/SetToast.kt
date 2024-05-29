package io.bash_psk.utils.toast

import android.content.Context
import android.widget.Toast

object SetToast {

    fun setToastShort(
        context: Context,
        message: String
    ) {

        Toast.makeText(
            context,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    fun setToastLong(
        context: Context,
        message: String
    ) {

        Toast.makeText(
            context,
            message,
            Toast.LENGTH_LONG
        ).show()
    }
}