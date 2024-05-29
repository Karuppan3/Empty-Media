package io.bash_psk.preference.color

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal fun CoroutineScope.pressCollector(
    interactionSource: InteractionSource,
    onOffset: (
        offset: Offset
    ) -> Unit
) {

    launch {

        interactionSource.interactions.collect { interaction: Interaction ->

            (interaction as? PressInteraction.Press)?.pressPosition?.let(onOffset)
        }
    }
}