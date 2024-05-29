package io.bash_psk.empty_media.presentation.dialog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Downloading
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import io.bash_psk.empty_media.domain.dialog.DownloadConfirmationState
import io.bash_psk.empty_media.domain.media.MediaFormatData
import io.bash_psk.empty_media.domain.resource.ConstantButton
import io.bash_psk.empty_media.domain.resource.ConstantDesc
import io.bash_psk.empty_media.domain.resource.ConstantTitle

@Composable
fun DownloadConfirmationDialog(
    downloadConfirmationState: DownloadConfirmationState,
    onDownloadConfirmationState: (
        confirmationState: DownloadConfirmationState
    ) -> Unit,
    onMediaFormatData: (
        media: MediaFormatData
    ) -> Unit
) {

    AnimatedVisibility(
        visible = downloadConfirmationState.isDownloadConfirmationDialog,
        enter = slideInVertically(),
        exit = slideOutVertically()
    ) {

        AlertDialog(
            modifier = Modifier.fillMaxWidth(),
            onDismissRequest = {

                val dismissDownloadConfigurationState = downloadConfirmationState.copy(
                    isDownloadConfirmationDialog = false
                )

                onDownloadConfirmationState(dismissDownloadConfigurationState)
            },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = false,
                usePlatformDefaultWidth = true,
                decorFitsSystemWindows = true
            ),
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.75f),
            titleContentColor = MaterialTheme.colorScheme.onTertiaryContainer,
            shape = MaterialTheme.shapes.medium,
            title = {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        modifier = Modifier.weight(weight = 1.0f),
                        text = ConstantTitle.DOWNLOAD_CONFIGURATION_DIALOG,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    IconButton(
                        onClick = {

                            val dismissDownloadConfigurationState = downloadConfirmationState.copy(
                                isDownloadConfirmationDialog = false
                            )

                            onDownloadConfirmationState(dismissDownloadConfigurationState)
                        }
                    ) {

                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = ConstantDesc.DIALOG_CLOSE
                        )
                    }
                }
            },
            text = {
            },
            confirmButton = {

                Button(
                    onClick = {

                        val dismissDownloadConfigurationState = downloadConfirmationState.copy(
                            isDownloadConfirmationDialog = false,
                            mediaFormatData = MediaFormatData(),
                            audioFormatData = MediaFormatData()
                        )

                        onDownloadConfirmationState(dismissDownloadConfigurationState)

                        onMediaFormatData(downloadConfirmationState.mediaFormatData)
                    }
                ) {

                    Icon(
                        imageVector = Icons.Filled.Downloading,
                        contentDescription = ConstantDesc.DOWNLOAD_BUTTON
                    )

                    Spacer(modifier = Modifier.size(size = 4.dp))

                    Text(text = ConstantButton.DOWNLOAD)
                }
            },
            dismissButton = {

                OutlinedButton(
                    onClick = {

                        val dismissDownloadConfigurationState = downloadConfirmationState.copy(
                            isDownloadConfirmationDialog = false,
                            mediaFormatData = MediaFormatData(),
                            audioFormatData = MediaFormatData()
                        )

                        onDownloadConfirmationState(dismissDownloadConfigurationState)
                    }
                ) {

                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = ConstantDesc.DISMISS_BUTTON
                    )

                    Spacer(modifier = Modifier.size(size = 4.dp))

                    Text(text = ConstantButton.DISMISS)
                }
            }
        )
    }
}