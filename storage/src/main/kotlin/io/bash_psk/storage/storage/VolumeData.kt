package io.bash_psk.storage.storage

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import java.util.UUID

@Stable
@Immutable
data class VolumeData(
    val uuid: UUID = UUID.randomUUID(),
    val name: String,
    val path: String,
    val isPrimary: Boolean,
    val isRemovable: Boolean,
    val totalSize: Long,
    val availableSize: Long,
    val usedSize: Long
)