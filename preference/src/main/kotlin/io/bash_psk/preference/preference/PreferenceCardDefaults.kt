package io.bash_psk.preference.preference

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal object PreferenceCardDefaults {

    val shape: Shape @Composable get() = MaterialTheme.shapes.extraSmall

    val containerColor: Color @Composable get() = MaterialTheme.colorScheme.background

    @Composable
    fun contentColor(isEnabled: Boolean) : Color {

        return when (isEnabled) {

            true -> {

                MaterialTheme.colorScheme.onBackground
            }

            false -> {

                MaterialTheme.colorScheme.onBackground
            }
        }
    }

    @Composable
    fun tonalElevation(isEnabled: Boolean) : Dp {

        return when (isEnabled) {

            true -> {

                0.dp
            }

            false -> {

                0.dp
            }
        }
    }

    @Composable
    fun shadowElevation(isEnabled: Boolean) : Dp {

        return when (isEnabled) {

            true -> {

                0.dp
            }

            false -> {

                0.dp
            }
        }
    }
}