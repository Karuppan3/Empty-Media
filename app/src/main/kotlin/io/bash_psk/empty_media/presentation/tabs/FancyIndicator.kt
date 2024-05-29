package io.bash_psk.empty_media.presentation.tabs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FancyIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary
) {

    Box(
        modifier = modifier
            .padding(
                paddingValues = PaddingValues(
                    vertical = 4.dp,
                    horizontal = 4.dp
                )
            )
            .fillMaxSize()
            .border(
                border = BorderStroke(
                    width = 1.5.dp,
                    color = color
                ),
                shape = MaterialTheme.shapes.extraSmall
            )
    )
}