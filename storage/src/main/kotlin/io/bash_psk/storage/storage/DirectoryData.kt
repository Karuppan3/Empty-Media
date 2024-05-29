package io.bash_psk.storage.storage

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import java.util.UUID

@Stable
@Immutable
data class DirectoryData(
    val uuid: UUID = UUID.randomUUID(),
    val name: String,
    val path: String,
    val parent: String,
    val isFolder: Boolean
)