package io.bash_psk.empty_media.presentation.dialog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Downloading
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
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
import io.bash_psk.empty_media.domain.dialog.FormatSelectionState
import io.bash_psk.empty_media.domain.media.MediaData
import io.bash_psk.empty_media.domain.media.MediaFormat
import io.bash_psk.empty_media.domain.resource.ConstantButton
import io.bash_psk.empty_media.domain.resource.ConstantDesc
import io.bash_psk.empty_media.domain.resource.ConstantTitle

@Composable
fun FormatSelectionDialog(
    formatSelectionState: FormatSelectionState,
    onFormatSelectionState: (
        formatState: FormatSelectionState
    ) -> Unit,
    onMediaDownload: (
        mediaList: List<MediaData>,
        format: MediaFormat
    ) -> Unit
) {

    AnimatedVisibility(
        visible = formatSelectionState.isFormatSelectionDialog,
        enter = slideInVertically(),
        exit = slideOutVertically()
    ) {

        AlertDialog(
            modifier = Modifier.fillMaxSize(),
            onDismissRequest = {

                val dismissFormatSelectionState = formatSelectionState.copy(
                    isFormatSelectionDialog = false
                )

                onFormatSelectionState(dismissFormatSelectionState)
            },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = false,
                usePlatformDefaultWidth = false,
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
                        text = ConstantTitle.FORMAT_SELECTION_DIALOG,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    IconButton(
                        onClick = {

                            val dismissFormatSelectionState = formatSelectionState.copy(
                                isFormatSelectionDialog = false
                            )

                            onFormatSelectionState(dismissFormatSelectionState)
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

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(space = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    items(
                        items = formatSelectionState.mediaFormatList,
                        key = { mediaFormat: MediaFormat ->

                            mediaFormat.name
                        }
                    ) { mediaFormat: MediaFormat ->

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {

                                    val selectFormatSelectionState = formatSelectionState.copy(
                                        mediaFormat = mediaFormat
                                    )

                                    onFormatSelectionState(selectFormatSelectionState)
                                },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
                        ) {

                            Checkbox(
                                checked = mediaFormat.name == formatSelectionState.mediaFormat.name,
                                onCheckedChange = { isChecked: Boolean ->

                                    val selectFormatSelectionState = formatSelectionState.copy(
                                        mediaFormat = mediaFormat
                                    )

                                    onFormatSelectionState(selectFormatSelectionState)
                                }
                            )

                            Text(
                                text = mediaFormat.name,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            },
            confirmButton = {

                Button(
                    onClick = {

                        val dismissFormatSelectionState = formatSelectionState.copy(
                            isFormatSelectionDialog = false,
                            mediaFormat = MediaFormat.INIT,
                            mediaList = emptyList()
                        )

                        onMediaDownload(
                            formatSelectionState.mediaList,
                            formatSelectionState.mediaFormat
                        )

                        onFormatSelectionState(dismissFormatSelectionState)
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

                        val dismissFormatSelectionState = formatSelectionState.copy(
                            isFormatSelectionDialog = false,
                            mediaFormat = MediaFormat.INIT,
                            mediaList = emptyList()
                        )

                        onFormatSelectionState(dismissFormatSelectionState)
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