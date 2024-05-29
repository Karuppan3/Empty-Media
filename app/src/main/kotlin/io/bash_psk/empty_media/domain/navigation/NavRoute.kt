package io.bash_psk.empty_media.domain.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import io.bash_psk.empty_media.domain.resource.ConstantNavigation

sealed class NavRoute(
    val route: String,
    val name: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector
) {

    data object Home : NavRoute(
        route = ConstantNavigation.HOME_ROUTE,
        name = ConstantNavigation.HOME_NAME,
        selectedIcon = ConstantNavigation.HOME_SELECTED_ICON,
        unSelectedIcon = ConstantNavigation.HOME_UNSELECTED_ICON
    )

    data object Downloads : NavRoute(
        route = ConstantNavigation.DOWNLOADS_ROUTE,
        name = ConstantNavigation.DOWNLOADS_NAME,
        selectedIcon = ConstantNavigation.DOWNLOADS_SELECTED_ICON,
        unSelectedIcon = ConstantNavigation.DOWNLOADS_UNSELECTED_ICON
    )

    data object Commands : NavRoute(
        route = ConstantNavigation.COMMANDS_ROUTE,
        name = ConstantNavigation.COMMANDS_NAME,
        selectedIcon = ConstantNavigation.COMMANDS_SELECTED_ICON,
        unSelectedIcon = ConstantNavigation.COMMANDS_UNSELECTED_ICON
    )

    data object Settings : NavRoute(
        route = ConstantNavigation.SETTINGS_ROUTE,
        name = ConstantNavigation.SETTINGS_NAME,
        selectedIcon = ConstantNavigation.SETTINGS_SELECTED_ICON,
        unSelectedIcon = ConstantNavigation.SETTINGS_UNSELECTED_ICON
    )

    data object VideoPlayer : NavRoute(
        route = ConstantNavigation.VIDEO_PLAYER_ROUTE,
        name = ConstantNavigation.VIDEO_PLAYER_NAME,
        selectedIcon = ConstantNavigation.VIDEO_PLAYER_SELECTED_ICON,
        unSelectedIcon = ConstantNavigation.VIDEO_PLAYER_UNSELECTED_ICON
    )

    data object AudioPlayer : NavRoute(
        route = ConstantNavigation.AUDIO_PLAYER_ROUTE,
        name = ConstantNavigation.AUDIO_PLAYER_NAME,
        selectedIcon = ConstantNavigation.AUDIO_PLAYER_SELECTED_ICON,
        unSelectedIcon = ConstantNavigation.AUDIO_PLAYER_UNSELECTED_ICON
    )
}