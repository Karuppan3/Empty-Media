package io.bash_psk.empty_media.domain.media

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import io.bash_psk.empty_media.domain.resource.ConstantString
import java.util.UUID

@Stable
@Immutable
data class MediaData(
    val uuid: UUID = UUID.randomUUID(),
    val actualUrl: String = ConstantString.EMPTY,
    val title: String = ConstantString.EMPTY,
    val thumbnail: String = ConstantString.EMPTY,
    val duration: Long = 0L,
    val mediaFormatList: List<MediaFormatData> = emptyList(),
    val isSearch: Boolean = false,
    val isEmpty: Boolean = false,
    val message: String = ConstantString.EMPTY
)