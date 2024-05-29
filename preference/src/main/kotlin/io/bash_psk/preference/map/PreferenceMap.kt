package io.bash_psk.preference.map

import io.bash_psk.preference.settings.AppTheme

@androidx.annotation.OptIn(
    androidx.media3.common.util.UnstableApi::class
)
object PreferenceMap {

    val APP_THEME_MAP = mapOf(
        AppTheme.DarkTheme.theme to AppTheme.DarkTheme.theme,
        AppTheme.LightTheme.theme to AppTheme.LightTheme.theme,
        AppTheme.SystemTheme.theme to AppTheme.SystemTheme.theme
    )
}