package io.bash_psk.preference.color

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.launch

@SuppressLint("ModifierFactoryUnreferencedReceiver")
internal fun Modifier.emitDragGesture(
    interactionSource: MutableInteractionSource
) : Modifier = composed {

    val coroutineScope = rememberCoroutineScope()

    pointerInput(key1 = Unit) {

        detectDragGestures { inputChange: PointerInputChange, dragAmount: Offset ->

            coroutineScope.launch {

                interactionSource.emit(
                    interaction = PressInteraction.Press(
                        pressPosition = inputChange.position
                    )
                )
            }
        }
    }.clickable(interactionSource = interactionSource, indication = null) {

    }
}