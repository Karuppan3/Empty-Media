package io.bash_psk.preference.settings

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable

@Composable
fun rememberAppTheme(theme: String) : Boolean {

    return when (theme) {

        AppTheme.DarkTheme.theme -> {

            true
        }

        AppTheme.LightTheme.theme -> {

            false
        }

        AppTheme.SystemTheme.theme -> {

            isSystemInDarkTheme()
        }

        else -> {

            isSystemInDarkTheme()
        }
    }
}