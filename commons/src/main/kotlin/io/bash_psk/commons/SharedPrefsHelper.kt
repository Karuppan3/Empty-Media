package io.bash_psk.commons

import android.content.Context

object SharedPrefsHelper {

    private const val SHARED_PREFERENCE_NAME = "EMPTY-MEDIA-PSK"

    @JvmStatic
    fun update(
        appContext: Context,
        key: String?,
        value: String?
    ) {

        val sharedPreferences = appContext.getSharedPreferences(
            SHARED_PREFERENCE_NAME,
            Context.MODE_PRIVATE
        )

        val editor = sharedPreferences.edit()

        editor.putString(key, value)
        editor.apply()
    }

    @JvmStatic
    operator fun get(
        appContext: Context,
        key: String?
    ) : String? {

        val sharedPreferences = appContext.getSharedPreferences(
            SHARED_PREFERENCE_NAME,
            Context.MODE_PRIVATE
        )

        return sharedPreferences.getString(key, null)
    }
}