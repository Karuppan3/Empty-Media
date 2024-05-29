package io.bash_psk.permission.permission

import android.os.Build
import androidx.annotation.RequiresApi
import io.bash_psk.permission.resource.ConstantPermission

sealed class Permissions(
    val permission: String,
    val description: String,
    val action: String
) {

    data object ReadStorage : Permissions(
        permission = ConstantPermission.READ_STORAGE_PERMISSION,
        description = ConstantPermission.READ_STORAGE_DESC,
        action = ConstantPermission.READ_STORAGE_ACTION
    )

    data object WriteStorage : Permissions(
        permission = ConstantPermission.WRITE_STORAGE_PERMISSION,
        description = ConstantPermission.WRITE_STORAGE_DESC,
        action = ConstantPermission.WRITE_STORAGE_ACTION
    )

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    data object ReadMediaVideo : Permissions(
        permission = ConstantPermission.READ_VIDEO_PERMISSION,
        description = ConstantPermission.READ_VIDEO_DESC,
        action = ConstantPermission.READ_VIDEO_ACTION
    )

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    data object PostNotification : Permissions(
        permission = ConstantPermission.POST_NOTIFICATION_PERMISSION,
        description = ConstantPermission.POST_NOTIFICATION_DESC,
        action = ConstantPermission.POST_NOTIFICATION_ACTION
    )
}