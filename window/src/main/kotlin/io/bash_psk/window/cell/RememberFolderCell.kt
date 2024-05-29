package io.bash_psk.window.cell

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import io.bash_psk.window.window.rememberWindowSize

@Composable
fun rememberFolderCell(
    folderCell: FolderCell
) : Int {

    val windowSize = rememberWindowSize()

    val folderShellCompact = when (windowSize.widthSizeClass) {

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

    val folderShellGrid = when (windowSize.widthSizeClass) {

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

    return when (folderCell) {

        FolderCell.COMPACT -> {

            folderShellCompact
        }

        FolderCell.GRID -> {

            folderShellGrid
        }
    }
}