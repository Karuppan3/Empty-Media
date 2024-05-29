package io.bash_psk.empty_media.presentation.media

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.bash_psk.empty_media.domain.media.MediaFormatData
import io.bash_psk.formatter.formatter.EmptyFormatter

@Composable
fun MediaFormatView(
    mediaFormatData: MediaFormatData,
    onMediaFormatData: (
        mediaFormat: MediaFormatData
    ) -> Unit
) {

    val context = LocalContext.current

    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.small,
        onClick = {

            onMediaFormatData(mediaFormatData)
        }
    ) {

        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(
                    paddingValues = PaddingValues(
                        horizontal = 8.dp,
                        vertical = 4.dp
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Text(
                modifier = Modifier.align(alignment = Alignment.CenterVertically),
                text = mediaFormatData.format,
                maxLines = 1,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                overflow = TextOverflow.Ellipsis
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(
                    paddingValues = PaddingValues(
                        horizontal = 8.dp,
                        vertical = 4.dp
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                modifier = Modifier.weight(weight = 1.0f),
                text = "Extension : ${mediaFormatData.ext}",
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                modifier = Modifier.weight(weight = 1.0f),
                text = "Resolution : ${mediaFormatData.resolution}",
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(
                    paddingValues = PaddingValues(
                        horizontal = 8.dp,
                        vertical = 4.dp
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                modifier = Modifier.weight(weight = 1.0f),
                text = "Format ID : ${mediaFormatData.formatId}",
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                modifier = Modifier.weight(weight = 1.0f),
                text = "FPS : ${mediaFormatData.fps}",
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(
                    paddingValues = PaddingValues(
                        horizontal = 8.dp,
                        vertical = 4.dp
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                modifier = Modifier.weight(weight = 1.0f),
                text = "Video Codec : ${mediaFormatData.vCodec}",
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                modifier = Modifier.weight(weight = 1.0f),
                text = "Audio Codec : ${mediaFormatData.aCodec}",
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(
                    paddingValues = PaddingValues(
                        horizontal = 8.dp,
                        vertical = 4.dp
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                modifier = Modifier.weight(weight = 1.0f),
                text = "File Size : ${
                    EmptyFormatter.fileSize(
                        context = context,
                        size = mediaFormatData.fileSize
                    )
                }",
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                modifier = Modifier.weight(weight = 1.0f),
                text = "Approximate Size : ${
                    EmptyFormatter.fileSize(
                        context = context,
                        size = mediaFormatData.approximateSize
                    )
                }",
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}