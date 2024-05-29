package io.bash_psk.empty_media.domain.dialog

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import io.bash_psk.empty_media.domain.media.MediaFormatData

@Stable
@Immutable
data class DownloadConfirmationState(
    val isDownloadConfirmationDialog: Boolean = false,
    val mediaFormatData: MediaFormatData = MediaFormatData(),
    val audioFormatData: MediaFormatData = MediaFormatData()
)