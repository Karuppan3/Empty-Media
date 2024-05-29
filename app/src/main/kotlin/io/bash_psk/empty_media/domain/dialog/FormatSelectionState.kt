package io.bash_psk.empty_media.domain.dialog

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import io.bash_psk.empty_media.domain.map.ListData
import io.bash_psk.empty_media.domain.media.MediaData
import io.bash_psk.empty_media.domain.media.MediaFormat

@Stable
@Immutable
data class FormatSelectionState(
    val isFormatSelectionDialog: Boolean = false,
    val mediaFormatList: List<MediaFormat> = ListData.MEDIA_FORMAT_LIST,
    val mediaFormat: MediaFormat = MediaFormat.INIT,
    val mediaList: List<MediaData> = emptyList()
)