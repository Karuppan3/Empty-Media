package io.bash_psk.empty_media.data.repository

import android.app.Activity
import android.app.Application
import io.bash_psk.empty_media.domain.repository.EmptyPermission
import io.bash_psk.permission.permission.PermissionIO
import io.bash_psk.permission.permission.PermissionState
import io.bash_psk.permission.permission.Permissions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EmptyPermissionImpl(
    private val application: Application
) : EmptyPermission {

    override fun allPermissions(
        activity: Activity,
        permissions: List<Permissions>
    ) : Flow<List<PermissionState>> {

        return flow {

            val permissionResult = PermissionIO.allPermissions(
                activity = activity,
                permissionList = permissions
            )

            emit(value = permissionResult)
        }
    }
}