package io.bash_psk.permission.permission

import android.os.Build

val REQUIRED_PERMISSIONS = when (Build.VERSION.SDK_INT) {

    Build.VERSION_CODES.UPSIDE_DOWN_CAKE -> {

        listOf(Permissions.ReadMediaVideo, Permissions.PostNotification)
    }

    Build.VERSION_CODES.TIRAMISU -> {

        listOf(Permissions.ReadMediaVideo, Permissions.PostNotification)
    }

    Build.VERSION_CODES.S_V2 -> {

        listOf(Permissions.ReadStorage, Permissions.WriteStorage)
    }

    Build.VERSION_CODES.S -> {

        listOf(Permissions.ReadStorage, Permissions.WriteStorage)
    }

    Build.VERSION_CODES.R -> {

        listOf(Permissions.ReadStorage, Permissions.WriteStorage)
    }

    Build.VERSION_CODES.Q -> {

        listOf(Permissions.ReadStorage, Permissions.WriteStorage)
    }

    Build.VERSION_CODES.P -> {

        listOf(Permissions.ReadStorage, Permissions.WriteStorage)
    }

    Build.VERSION_CODES.O_MR1 -> {

        listOf(Permissions.ReadStorage, Permissions.WriteStorage)
    }

    Build.VERSION_CODES.O -> {

        listOf(Permissions.ReadStorage, Permissions.WriteStorage)
    }

    Build.VERSION_CODES.N_MR1 -> {

        listOf(Permissions.ReadStorage, Permissions.WriteStorage)
    }

    Build.VERSION_CODES.N -> {

        listOf(Permissions.ReadStorage, Permissions.WriteStorage)
    }

    else -> {

        listOf(Permissions.ReadStorage, Permissions.WriteStorage)
    }
}