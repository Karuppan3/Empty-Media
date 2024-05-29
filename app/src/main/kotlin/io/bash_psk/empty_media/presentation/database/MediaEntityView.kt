package io.bash_psk.empty_media.presentation.database

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.bash_psk.empty_media.domain.database.MediaEntity
import io.bash_psk.formatter.formatter.EmptyFormatter
import io.bash_psk.formatter.formatter.FormatterPattern

@Composable
fun MediaEntityView(
    mediaEntity: MediaEntity,
    onMediaEntity: (
        entity: MediaEntity
    ) -> Unit
) {

    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.small,
        onClick = {

            onMediaEntity(mediaEntity)
        }
    ) {

        Text(
            modifier = Modifier.padding(
                paddingValues = PaddingValues(
                    vertical = 4.dp,
                    horizontal = 12.dp
                )
            ),
            text = mediaEntity.title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            modifier = Modifier.padding(
                paddingValues = PaddingValues(
                    vertical = 4.dp,
                    horizontal = 12.dp
                )
            ),
            text = mediaEntity.status,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            modifier = Modifier.padding(
                paddingValues = PaddingValues(
                    vertical = 4.dp,
                    horizontal = 12.dp
                )
            ),
            text = EmptyFormatter.dateTime(
                dateTime = mediaEntity.timeStamp, pattern = FormatterPattern.SHORT_DATE_TIME
            ),
            style = MaterialTheme.typography.labelSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}