package io.bash_psk.permission.permission

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import io.bash_psk.permission.resource.ConstantButton
import io.bash_psk.permission.resource.ConstantDesc
import io.bash_psk.permission.utils.SettingsAction
import io.bash_psk.permission.utils.getActivity

@Composable
fun PermissionSmall(
    permissionState: PermissionState,
    onPermissionResult: () -> Unit
) {

    val context = LocalContext.current
    val activity = context.getActivity()

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted: Boolean ->

            when (isGranted) {

                true -> {

                    onPermissionResult()
                }

                false -> {

                    onPermissionResult()
                }
            }
        }
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(space = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HorizontalDivider()

        Text(
            text = permissionState.description,
            style = MaterialTheme.typography.titleMedium
        )

        Button(
            onClick = {

                when (permissionState.isRationale) {

                    true -> {

                        SettingsAction.settingAction(
                            activity = activity,
                            action = permissionState.action
                        )
                    }

                    false -> {

                        permissionLauncher.launch(permissionState.permission)
                    }
                }
            }
        ) {

            Icon(
                imageVector = when (permissionState.isGranted) {

                    true -> {

                        Icons.Filled.Check
                    }

                    false -> {

                        Icons.Filled.Close
                    }
                },
                contentDescription = ConstantDesc.PERMISSION_STATE_DESC
            )

            Text(text = ConstantButton.GRANT_PERMISSION_BUTTON)
        }

        HorizontalDivider()
    }
}