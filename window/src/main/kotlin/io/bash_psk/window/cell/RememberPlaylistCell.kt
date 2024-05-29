package io.bash_psk.window.cell

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import io.bash_psk.window.window.rememberWindowSize

@Composable
fun rememberPlaylistCell(
    playlistCell: PlaylistCell
) : Int {

    val windowSize = rememberWindowSize()

    val playlistShellCompact = when (windowSize.widthSizeClass) {

        WindowWidthSizeClass.Compact -> {

            2
        }

        WindowWidthSizeClass.Medium -> {

            3
        }

        WindowWidthSizeClass.Expanded -> {

            4
        }

        else -> {

            6
        }
    }

    val playlistShellGrid = when (windowSize.widthSizeClass) {

        WindowWidthSizeClass.Compact -> {

            3
        }

        WindowWidthSizeClass.Medium -> {

            4
        }

        WindowWidthSizeClass.Expanded -> {

            5
        }

        else -> {

            7
        }
    }

    return when (playlistCell) {

        PlaylistCell.COMPACT -> {

            playlistShellCompact
        }

        PlaylistCell.GRID -> {

            playlistShellGrid
        }
    }
}