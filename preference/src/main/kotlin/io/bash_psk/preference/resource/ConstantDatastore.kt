package io.bash_psk.preference.resource

import androidx.datastore.preferences.core.stringPreferencesKey

internal object ConstantDatastore {

    const val PSK = "PSK"
    const val BASH_PSK = "Bash PSK"

    const val DATASTORE_NAME = "EMPTY-MEDIA-PSK"

    const val EMPTY = ""
    const val NONE = "None"
    
    const val PROFILE_NAME_TITLE = "Application Profile Name"
    const val APPLICATION_THEME_TITLE = "Application Theme"
    const val YTDL_LIBRARY_TITLE = "Ytdl Library"

    val PROFILE_NAME_KEY = stringPreferencesKey(name = "PROFILE NAME KEY")
    val APPLICATION_THEME_KEY = stringPreferencesKey(name = "APPLICATION THEME KEY")
    val YTDL_LIBRARY_KEY = stringPreferencesKey(name = "YTDL LIBRARY KEY")

    const val PROFILE_NAME_INITIAL = "Bash PSK"
    const val YTDL_LIBRARY_INITIAL = "2024.04.09"
}