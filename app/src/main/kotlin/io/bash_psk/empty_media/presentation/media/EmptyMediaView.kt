package io.bash_psk.empty_media.presentation.media

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import io.bash_psk.empty_media.domain.resource.ConstantDesc
import io.bash_psk.empty_media.domain.resource.ConstantString

@Composable
fun EmptyMediaView(
    isEmptyMediaView: Boolean
) {

    AnimatedVisibility(
        visible = isEmptyMediaView,
        enter = slideInVertically(),
        exit = slideOutVertically()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    paddingValues = PaddingValues(
                        vertical = 12.dp,
                        horizontal = 12.dp
                    )
                ),
            verticalArrangement = Arrangement.spacedBy(space = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                modifier = Modifier.size(width = 100.dp, height = 100.dp)
                    .alpha(alpha = 0.75f),
                imageVector = Icons.Filled.FolderOpen,
                contentDescription = ConstantDesc.NO_MEDIA_FOUND
            )

            Text(
                modifier = Modifier.alpha(alpha = 0.75f),
                text = ConstantString.NO_MEDIA_FOUND,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}