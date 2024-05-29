package io.bash_psk.empty_media.domain.repository

import android.app.Activity
import io.bash_psk.permission.permission.PermissionState
import io.bash_psk.permission.permission.Permissions
import kotlinx.coroutines.flow.Flow

interface EmptyPermission {

    fun allPermissions(
        activity: Activity,
        permissions: List<Permissions>
    ) : Flow<List<PermissionState>>
}