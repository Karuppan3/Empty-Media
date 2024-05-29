package io.bash_psk.window.cell

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import io.bash_psk.window.window.rememberWindowSize

@Composable
fun rememberVideoCell(
    videoCell: VideoCell
) : Int {

    val windowSize = rememberWindowSize()

    val videoShellCompact = when (windowSize.widthSizeClass) {

        WindowWidthSizeClass.Compact -> {

            1
        }

        WindowWidthSizeClass.Medium -> {

            2
        }

        WindowWidthSizeClass.Expanded -> {

            3
        }

        else -> {

            5
        }
    }

    val videoShellGrid = when (windowSize.widthSizeClass) {

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

    return when (videoCell) {

        VideoCell.COMPACT -> {

            videoShellCompact
        }

        VideoCell.GRID -> {

            videoShellGrid
        }
    }
}