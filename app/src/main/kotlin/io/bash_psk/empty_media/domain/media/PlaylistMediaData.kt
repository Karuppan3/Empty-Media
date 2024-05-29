package io.bash_psk.empty_media.domain.media

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import io.bash_psk.empty_media.domain.resource.ConstantString
import java.util.UUID

@Stable
@Immutable
data class PlaylistMediaData(
    val uuid: UUID = UUID.randomUUID(),
    val title: String = ConstantString.EMPTY,
    val mediaList: List<MediaData> = emptyList(),
    val isSearch: Boolean = false,
    val isEmpty: Boolean = false,
    val message: String = ConstantString.EMPTY
)