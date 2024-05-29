package io.bash_psk.empty_media.presentation.selection

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

@Composable
fun <T> rememberMediaSelect(
    media: T,
    selectedMedias: List<T>
) : Boolean {

    val lifecycleOwner = LocalLifecycleOwner.current

    val isSelected = rememberSaveable {
        mutableStateOf(value = false)
    }

    DisposableEffect(
        key1 = lifecycleOwner.lifecycle,
        key2 = media,
        key3 = selectedMedias
    ) {

        val observer = LifecycleEventObserver { _, event: Lifecycle.Event ->

            isSelected.value = selectedMedias.contains(element = media)
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {

            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    return isSelected.value
}