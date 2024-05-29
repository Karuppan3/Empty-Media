package io.bash_psk.permission.permission

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import java.util.UUID

@Stable
@Immutable
data class PermissionState(
    val uuid: UUID = UUID.randomUUID(),
    val permission: String,
    val description: String,
    val isGranted: Boolean,
    val isRationale: Boolean,
    val action: String
)