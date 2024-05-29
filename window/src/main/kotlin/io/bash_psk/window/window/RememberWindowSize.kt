package io.bash_psk.window.window

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import io.bash_psk.window.utils.getActivity

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun rememberWindowSize() : WindowSizeClass {

    val context = LocalContext.current
    val activity = context.getActivity()

    return calculateWindowSizeClass(activity = activity)
}