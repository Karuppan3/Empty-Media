package io.bash_psk.storage.dialog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.window.DialogProperties
import io.bash_psk.storage.resource.ConstantButton
import io.bash_psk.storage.resource.ConstantDesc
import io.bash_psk.storage.resource.ConstantString

@Composable
fun CreateDirectory(
    isCreateDirectoryDialog: Boolean,
    onCreateDirectoryDialog: (
        isDirectory: Boolean
    ) -> Unit,
    onCreateDirectory: (
        directory: String
    ) -> Unit
) {

    val directoryName = rememberSaveable {
        mutableStateOf(value = ConstantString.EMPTY)
    }

    AnimatedVisibility(
        visible = isCreateDirectoryDialog,
        enter = fadeIn(),
        exit = fadeOut()
    ) {

        AlertDialog(
            modifier = Modifier.fillMaxWidth(),
            onDismissRequest = {

                onCreateDirectoryDialog(false)
            },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = false
            ),
            title = {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = ConstantString.NEW_DIRECTORY,
                        style = MaterialTheme.typography.titleMedium
                    )

                    IconButton(
                        onClick = {

                            directoryName.value = ConstantString.EMPTY
                            onCreateDirectoryDialog(false)
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

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = directoryName.value,
                    onValueChange = { directory: String ->

                        directoryName.value = directory
                    },
                    label = {

                        Text(text = ConstantString.NEW_DIRECTORY)
                    },
                    placeholder = {

                        Text(text = ConstantString.ENTER_DIRECTORY_NAME)
                    },
                    keyboardOptions = KeyboardOptions(
                        autoCorrect = false,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    )
                )
            },
            confirmButton = {

                Button(
                    onClick = {

                        onCreateDirectory(directoryName.value)
                        directoryName.value = ConstantString.EMPTY
                        onCreateDirectoryDialog(false)
                    }
                ) {

                    Text(text = ConstantButton.OK_BUTTON)
                }
            },
            dismissButton = {

                Button(
                    onClick = {

                        directoryName.value = ConstantString.EMPTY
                        onCreateDirectoryDialog(false)
                    }
                ) {

                    Text(text = ConstantButton.DISMISS_BUTTON)
                }
            }
        )
    }
}