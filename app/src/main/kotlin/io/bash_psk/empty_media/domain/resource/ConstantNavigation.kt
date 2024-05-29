package io.bash_psk.empty_media.domain.resource

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material.icons.automirrored.outlined.Comment
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MusicVideo
import androidx.compose.material.icons.filled.OndemandVideo
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MusicVideo
import androidx.compose.material.icons.outlined.OndemandVideo
import androidx.compose.material.icons.outlined.Settings

object ConstantNavigation {

    const val HOME_ROUTE = "HOME"
    const val DOWNLOADS_ROUTE = "DOWNLOADS"
    const val COMMANDS_ROUTE = "COMMANDS"
    const val SETTINGS_ROUTE = "SETTINGS"
    const val VIDEO_PLAYER_ROUTE = "VIDEO PLAYER"
    const val AUDIO_PLAYER_ROUTE = "AUDIO PLAYER"

    const val HOME_NAME = "Home"
    const val DOWNLOADS_NAME = "Downloads"
    const val COMMANDS_NAME = "Commands"
    const val SETTINGS_NAME = "Settings"
    const val VIDEO_PLAYER_NAME = "Video Player"
    const val AUDIO_PLAYER_NAME = "Audio Player"

    val HOME_SELECTED_ICON = Icons.Filled.Home
    val DOWNLOADS_SELECTED_ICON = Icons.Filled.Download
    val COMMANDS_SELECTED_ICON = Icons.AutoMirrored.Filled.Comment
    val SETTINGS_SELECTED_ICON = Icons.Filled.Settings
    val VIDEO_PLAYER_SELECTED_ICON = Icons.Filled.OndemandVideo
    val AUDIO_PLAYER_SELECTED_ICON = Icons.Filled.MusicVideo

    val HOME_UNSELECTED_ICON = Icons.Outlined.Home
    val DOWNLOADS_UNSELECTED_ICON = Icons.Outlined.Download
    val COMMANDS_UNSELECTED_ICON = Icons.AutoMirrored.Outlined.Comment
    val SETTINGS_UNSELECTED_ICON = Icons.Outlined.Settings
    val VIDEO_PLAYER_UNSELECTED_ICON = Icons.Outlined.OndemandVideo
    val AUDIO_PLAYER_UNSELECTED_ICON = Icons.Outlined.MusicVideo
}