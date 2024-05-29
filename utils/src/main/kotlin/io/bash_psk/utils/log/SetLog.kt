package io.bash_psk.utils.log

import android.util.Log

object SetLog {

    private const val APP_TAG = "EMPTY-MEDIA"

    fun setDebug(message: String) {

        Log.d(APP_TAG, message)
    }

    fun setDebug(message: String?, throwable: Throwable?) {

        Log.d(APP_TAG, message, throwable)
    }

    fun setError(message: String) {

        Log.e(APP_TAG, message)
    }

    fun setError(message: String?, throwable: Throwable?) {

        Log.e(APP_TAG, message, throwable)
    }

    fun setInfo(message: String) {

        Log.i(APP_TAG, message)
    }

    fun setInfo(message: String?, throwable: Throwable?) {

        Log.i(APP_TAG, message, throwable)
    }

    fun setVerbose(message: String) {

        Log.v(APP_TAG, message)
    }

    fun setVerbose(message: String?, throwable: Throwable?) {

        Log.v(APP_TAG, message, throwable)
    }

    fun setWarning(message: String) {

        Log.w(APP_TAG, message)
    }

    fun setWarning(throwable: Throwable?) {

        Log.w(APP_TAG, throwable)
    }

    fun setWarning(message: String?, throwable: Throwable?) {

        Log.w(APP_TAG, message, throwable)
    }
}