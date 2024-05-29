package io.bash_psk.empty_media.presentation.worker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.bash_psk.empty_media.domain.worker.CommandWorkerState

@Composable
fun DownloadWorkerView(
    downloadWorkerState: CommandWorkerState
) {
    
    val downloadState = remember(key1 = downloadWorkerState) {
        downloadWorkerState
    }

    Column(
        modifier = Modifier.wrapContentSize(),
        verticalArrangement = Arrangement.spacedBy(space = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "${downloadState.message} - ${downloadState.title}",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = "${downloadState.elapsedTime} / ${downloadState.progress}%",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth(),
            progress = {

                downloadState.progress / 100.0f
            }
        )
    }
}