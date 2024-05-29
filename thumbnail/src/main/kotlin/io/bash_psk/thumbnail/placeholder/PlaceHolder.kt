package io.bash_psk.thumbnail.placeholder

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.MusicVideo
import androidx.compose.material.icons.filled.OndemandVideo
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import io.bash_psk.thumbnail.resource.ConstantDesc

@Composable
fun PlaceHolder(
    modifier: Modifier = Modifier,
    placeHolderType: PlaceHolderType
) {

    Box(
        modifier = modifier.background(color = MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ) {

        Image(
            modifier = modifier.fillMaxSize(),
            imageVector = when (placeHolderType) {

                PlaceHolderType.AUDIO -> {

                    Icons.Filled.MusicVideo
                }

                PlaceHolderType.IMAGE -> {

                    Icons.Filled.Image
                }

                PlaceHolderType.VIDEO -> {

                    Icons.Filled.OndemandVideo
                }
            },
            contentScale = ContentScale.Fit,
            contentDescription = ConstantDesc.THUMBNAIL_DESC
        )
    }
}