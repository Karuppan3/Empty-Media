package io.bash_psk.empty_media.domain.media

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import io.bash_psk.empty_media.domain.resource.ConstantString
import java.util.UUID

@Stable
@Immutable
data class MediaFormatData(
    val uuid: UUID = UUID.randomUUID(),
    val actualUrl: String = ConstantString.EMPTY,
    val asr: Int = 0,
    val tbr: Int = 0,
    val abr: Int = 0,
    val url: String = ConstantString.EMPTY,
    val formatId: String = ConstantString.EMPTY,
    val format: String = ConstantString.EMPTY,
    val formatNote: String = ConstantString.EMPTY,
    val resolution: String = ConstantString.EMPTY,
    val fileSize: Long = 0L,
    val approximateSize: Long = 0L,
    val fps: Int = 0,
    val vCodec: String = ConstantString.EMPTY,
    val aCodec: String = ConstantString.EMPTY,
    val ext: String = ConstantString.EMPTY,
    val isVideoOnly: Boolean = false
)