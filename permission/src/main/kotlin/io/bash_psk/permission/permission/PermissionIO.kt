package io.bash_psk.permission.permission

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

object PermissionIO {

    fun allPermissions(
        activity: Activity,
        permissionList: List<Permissions>
    ) : List<PermissionState> {

        val permissionResult = mutableListOf<PermissionState>()

        permissionList.forEach { permissions: Permissions ->

            when (Build.VERSION.SDK_INT) {

                Build.VERSION_CODES.UPSIDE_DOWN_CAKE -> {

                    val permissionState = when (permissions) {

                        Permissions.PostNotification -> {

                            notificationPermission(activity = activity, permissions = permissions)
                        }

                        Permissions.ReadStorage -> {

                            checkPermission(activity = activity, permissions = permissions)
                        }

                        Permissions.ReadMediaVideo -> {

                            checkPermission(activity = activity, permissions = permissions)
                        }

                        Permissions.WriteStorage -> {

                            checkPermission(activity = activity, permissions = permissions)
                        }
                    }

                    permissionResult.add(element = permissionState)
                }

                Build.VERSION_CODES.TIRAMISU -> {

                    val permissionState = when (permissions) {

                        Permissions.PostNotification -> {

                            notificationPermission(activity = activity, permissions = permissions)
                        }

                        Permissions.ReadStorage -> {

                            checkPermission(activity = activity, permissions = permissions)
                        }

                        Permissions.ReadMediaVideo -> {

                            checkPermission(activity = activity, permissions = permissions)
                        }

                        Permissions.WriteStorage -> {

                            checkPermission(activity = activity, permissions = permissions)
                        }
                    }

                    permissionResult.add(element = permissionState)
                }

                Build.VERSION_CODES.S_V2 -> {

                    val permissionState = when (permissions) {

                        Permissions.ReadStorage -> {

                            checkPermission(activity = activity, permissions = permissions)
                        }

                        Permissions.WriteStorage -> {

                            checkPermission(activity = activity, permissions = permissions)
                        }

                        else -> {

                            checkPermission(activity = activity, permissions = permissions)
                        }
                    }

                    permissionResult.add(element = permissionState)
                }

                Build.VERSION_CODES.S -> {

                    val permissionState = when (permissions) {

                        Permissions.ReadStorage -> {

                            checkPermission(activity = activity, permissions = permissions)
                        }

                        Permissions.WriteStorage -> {

                            checkPermission(activity = activity, permissions = permissions)
                        }

                        else -> {

                            checkPermission(activity = activity, permissions = permissions)
                        }
                    }

                    permissionResult.add(element = permissionState)
                }

                Build.VERSION_CODES.R -> {

                    val permissionState = when (permissions) {

                        Permissions.ReadStorage -> {

                            checkPermission(activity = activity, permissions = permissions)
                        }

                        Permissions.WriteStorage -> {

                            checkPermission(activity = activity, permissions = permissions)
                        }

                        else -> {

                            checkPermission(activity = activity, permissions = permissions)
                        }
                    }

                    permissionResult.add(element = permissionState)
                }

                Build.VERSION_CODES.Q -> {

                    val permissionState = when (permissions) {

                        Permissions.ReadStorage -> {

                            checkPermission(activity = activity, permissions = permissions)
                        }

                        Permissions.WriteStorage -> {

                            checkPermission(activity = activity, permissions = permissions)
                        }

                        else -> {

                            checkPermission(activity = activity, permissions = permissions)
                        }
                    }

                    permissionResult.add(element = permissionState)
                }

                Build.VERSION_CODES.P -> {

                    val permissionState = when (permissions) {

                        Permissions.ReadStorage -> {

                            checkPermission(activity = activity, permissions = permissions)
                        }

                        Permissions.WriteStorage -> {

                            checkPermission(activity = activity, permissions = permissions)
                        }

                        else -> {

                            checkPermission(activity = activity, permissions = permissions)
                        }
                    }

                    permissionResult.add(element = permissionState)
                }

                Build.VERSION_CODES.O_MR1 -> {

                    val permissionState = when (permissions) {

                        Permissions.ReadStorage -> {

                            checkPermission(activity = activity, permissions = permissions)
                        }

                        Permissions.WriteStorage -> {

                            checkPermission(activity = activity, permissions = permissions)
                        }

                        else -> {

                            checkPermission(activity = activity, permissions = permissions)
                        }
                    }

                    permissionResult.add(element = permissionState)
                }

                Build.VERSION_CODES.O -> {

                    val permissionState = when (permissions) {

                        Permissions.ReadStorage -> {

                            checkPermission(activity = activity, permissions = permissions)
                        }

                        Permissions.WriteStorage -> {

                            checkPermission(activity = activity, permissions = permissions)
                        }

                        else -> {

                            checkPermission(activity = activity, permissions = permissions)
                        }
                    }

                    permissionResult.add(element = permissionState)
                }

                Build.VERSION_CODES.N_MR1 -> {

                    val permissionState = when (permissions) {

                        Permissions.ReadStorage -> {

                            checkPermission(activity = activity, permissions = permissions)
                        }

                        Permissions.WriteStorage -> {

                            checkPermission(activity = activity, permissions = permissions)
                        }

                        else -> {

                            checkPermission(activity = activity, permissions = permissions)
                        }
                    }

                    permissionResult.add(element = permissionState)
                }

                Build.VERSION_CODES.N -> {

                    val permissionState = when (permissions) {

                        Permissions.ReadStorage -> {

                            checkPermission(activity = activity, permissions = permissions)
                        }

                        Permissions.WriteStorage -> {

                            checkPermission(activity = activity, permissions = permissions)
                        }

                        else -> {

                            checkPermission(activity = activity, permissions = permissions)
                        }
                    }

                    permissionResult.add(element = permissionState)
                }

                else -> {

                    val permissionState = when (permissions) {

                        Permissions.ReadStorage -> {

                            checkPermission(activity = activity, permissions = permissions)
                        }

                        Permissions.WriteStorage -> {

                            checkPermission(activity = activity, permissions = permissions)
                        }

                        else -> {

                            checkPermission(activity = activity, permissions = permissions)
                        }
                    }

                    permissionResult.add(element = permissionState)
                }
            }
        }

        return permissionResult.toList()
    }

    private fun checkPermission(
        activity: Activity,
        permissions: Permissions
    ) : PermissionState {

        val isGranted = ContextCompat.checkSelfPermission(
            activity,
            permissions.permission
        ) == PackageManager.PERMISSION_GRANTED

        val isRationale = activity.shouldShowRequestPermissionRationale(
            permissions.permission
        )

        return PermissionState(
            permission = permissions.permission,
            description = permissions.description,
            isGranted = isGranted,
            isRationale = isRationale,
            action = permissions.action
        )
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun manageStoragePermission(
        permissions: Permissions
    ) : PermissionState {

        val isGranted = Environment.isExternalStorageManager()
        val isRationale = true

        return PermissionState(
            permission = permissions.permission,
            description = permissions.description,
            isGranted = isGranted,
            isRationale = isRationale,
            action = permissions.action
        )
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun notificationPermission(
        activity: Activity,
        permissions: Permissions
    ) : PermissionState {

        val isGranted = ContextCompat.checkSelfPermission(
            activity,
            permissions.permission
        ) == PackageManager.PERMISSION_GRANTED

        val isRationale = activity.shouldShowRequestPermissionRationale(
            permissions.permission
        )

        return PermissionState(
            permission = permissions.permission,
            description = permissions.description,
            isGranted = isGranted,
            isRationale = isRationale,
            action = permissions.action
        )
    }
}