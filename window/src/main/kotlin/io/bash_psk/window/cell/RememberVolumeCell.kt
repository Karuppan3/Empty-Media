package io.bash_psk.window.cell

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import io.bash_psk.window.window.rememberWindowSize

@Composable
fun rememberVolumeCell() : Int {

    val windowSize = rememberWindowSize()

    val volumeShellCompact = when (windowSize.widthSizeClass) {

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

    return volumeShellCompact
}