package io.bash_psk.preference.settings

import io.bash_psk.preference.resource.ConstantSetting

sealed class AppTheme(
    val theme: String
) {

    data object DarkTheme : AppTheme(
        theme = ConstantSetting.DARK_THEME
    )

    data object LightTheme : AppTheme(
        theme = ConstantSetting.LIGHT_THEME
    )

    data object SystemTheme : AppTheme(
        theme = ConstantSetting.SYSTEM_THEME
    )
}