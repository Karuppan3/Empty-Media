package io.bash_psk.permission.permission

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import io.bash_psk.permission.resource.ConstantDesc
import io.bash_psk.permission.resource.ConstantTitle
import io.bash_psk.permission.utils.getActivity

@Composable
fun PermissionDialog(
    isPermissionDialog: Boolean,
    onPermissionDialog: (
        isPermission: Boolean
    ) -> Unit,
    permissionStateList: List<PermissionState>,
    onPermissionResult: () -> Unit
) {

    val context = LocalContext.current
    val activity = context.getActivity()

    AnimatedVisibility(
        visible = isPermissionDialog,
        enter = fadeIn(),
        exit = fadeOut()
    ) {

        AlertDialog(
            modifier = Modifier.fillMaxSize(),
            onDismissRequest = {

                onPermissionDialog(false)
            },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = false,
                usePlatformDefaultWidth = false,
                decorFitsSystemWindows = true
            ),
            shape = MaterialTheme.shapes.small,
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.70f),
            title = {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        modifier = Modifier.weight(weight = 1.0f),
                        text = ConstantTitle.PERMISSION_REQUIRED_DIALOG_TITLE,
                        maxLines = 1,
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )

                    IconButton(
                        onClick = {

                            onPermissionDialog(false)
                        }
                    ) {

                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = ConstantDesc.DIALOG_CLOSE_DESC
                        )
                    }
                }
            },
            text = {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(space = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    items(
                        items = permissionStateList
                    ) { permissionState: PermissionState ->

                        PermissionSmall(
                            permissionState = permissionState,
                            onPermissionResult = onPermissionResult
                        )
                    }
                }
            },
            confirmButton = {
            }
        )
    }
}