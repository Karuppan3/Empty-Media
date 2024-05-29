package io.bash_psk.empty_media.presentation.media

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.bash_psk.empty_media.domain.media.MediaData
import io.bash_psk.empty_media.domain.resource.ConstantDesc
import io.bash_psk.empty_media.presentation.selection.rememberMediaSelect
import io.bash_psk.formatter.formatter.EmptyFormatter
import io.bash_psk.thumbnail.placeholder.PlaceHolder
import io.bash_psk.thumbnail.placeholder.PlaceHolderType
import io.bash_psk.thumbnail.thumbnail.rememberThumbnail

@Composable
fun MediaView(
    mediaData: MediaData,
    isMediaSelect: Boolean,
    selectedMediaList: List<MediaData>,
    onMediaData: (
        media: MediaData
    ) -> Unit
) {

    val context = LocalContext.current

    val contentId = mediaData.thumbnail.hashCode()
    val contentUrl = mediaData.thumbnail

    val mediaThumbnail = rememberThumbnail(contentId = contentId, contentUrl = contentUrl)
    val isSelected = rememberMediaSelect(media = mediaData, selectedMedias = selectedMediaList)

    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        onClick = {

            onMediaData(mediaData)
        },
        shape = MaterialTheme.shapes.small
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 225.dp),
            contentAlignment = Alignment.BottomStart
        ) {

            mediaThumbnail?.let { thumbnail: ImageBitmap ->

                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(shape = MaterialTheme.shapes.small),
                    bitmap = thumbnail,
                    contentDescription = ConstantDesc.THUMBNAIL
                )
            } ?: PlaceHolder(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = MaterialTheme.shapes.small)
                    .background(color = MaterialTheme.colorScheme.primaryContainer),
                placeHolderType = PlaceHolderType.VIDEO
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        paddingValues = PaddingValues(
                            vertical = 8.dp,
                            horizontal = 8.dp
                        )
                    ),
                verticalArrangement = Arrangement.spacedBy(space = 4.dp),
                horizontalAlignment = Alignment.Start
            ) {

                Text(
                    text = mediaData.title,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = EmptyFormatter.durationFormatter(duration = mediaData.duration.toLong()),
                    maxLines = 1,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    overflow = TextOverflow.Ellipsis
                )
            }

            when {

                isMediaSelect -> {

                    RadioButton(
                        modifier = Modifier.align(alignment = Alignment.TopEnd),
                        selected = isSelected,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = MaterialTheme.colorScheme.tertiaryContainer,
                            unselectedColor = MaterialTheme.colorScheme.tertiaryContainer
                        ),
                        onClick = {

                            onMediaData(mediaData)
                        }
                    )
                }
            }
        }
    }
}