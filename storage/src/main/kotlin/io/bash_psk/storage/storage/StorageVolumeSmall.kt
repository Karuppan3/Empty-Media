package io.bash_psk.storage.storage

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import io.bash_psk.formatter.formatter.EmptyFormatter
import io.bash_psk.storage.resource.ConstantString

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StorageVolumeSmall(
    volumeData: VolumeData,
    onStorageVolume: (
        volume: VolumeData
    ) -> Unit
) {

    val context = LocalContext.current

    val volumeUsedSize = EmptyFormatter.fileSize(
        context = context,
        size = volumeData.usedSize
    )

    val volumeAvailableSize = EmptyFormatter.fileSize(
        context = context,
        size = volumeData.availableSize
    )

    val volumeTotalSize = EmptyFormatter.fileSize(
        context = context,
        size = volumeData.totalSize
    )

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = {

                    onStorageVolume(volumeData)
                },
                onLongClick = {

                }
            ),
        shape = MaterialTheme.shapes.small
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    paddingValues = PaddingValues(
                        vertical = 8.dp,
                        horizontal = 8.dp
                    )
                ),
            verticalArrangement = Arrangement.spacedBy(space = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = volumeData.name,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            StorageSpaceRow(
                title = ConstantString.USED_STORAGE,
                space = volumeUsedSize
            )

            StorageSpaceRow(
                title = ConstantString.AVAILABLE_STORAGE,
                space = volumeAvailableSize
            )

            StorageSpaceRow(
                title = ConstantString.TOTAL_STORAGE,
                space = volumeTotalSize
            )
        }
    }
}