package io.bash_psk.preference.preference

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.bash_psk.preference.datastore.PreferenceData
import io.bash_psk.preference.datastore.getPreference
import io.bash_psk.preference.datastore.setPreference
import io.bash_psk.preference.resource.ConstantButton

@Composable
fun TextFieldPreference(
    preferenceData: PreferenceData<String, String, String>,
    dialogTitle: String,
    fieldLabel: String,
    isEnabled: Boolean = true,
    isFieldReadOnly: Boolean = false,
    fieldMaxLines: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        capitalization = KeyboardCapitalization.None,
        autoCorrect = true,
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Done
    ),
    isDismissOnBackPress: Boolean = true,
    isDismissOnClickOutside: Boolean = true
) {

    val context = LocalContext.current

    val getFieldText by context.getPreference(
        key = preferenceData.key,
        initial = preferenceData.initial
    ).collectAsStateWithLifecycle(initialValue = preferenceData.initial)


    val isDialog = rememberSaveable {
        mutableStateOf(value = false)
    }

    val textFieldText = rememberSaveable {
        mutableStateOf(value = getFieldText)
    }

    AnimatedVisibility(
        visible = isDialog.value,
        enter = fadeIn(),
        exit = fadeOut()
    ) {

        AlertDialog(
            modifier = Modifier.fillMaxWidth(fraction = 0.90f),
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnBackPress = isDismissOnBackPress,
                dismissOnClickOutside = isDismissOnClickOutside
            ),
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.70f),
            onDismissRequest = {

                isDialog.value = isDialog.value.not()
            },
            title = {

                Text(
                    text = dialogTitle,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleMedium,
                    overflow = TextOverflow.Ellipsis
                )
            },
            text = {

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = textFieldText.value,
                    onValueChange = { text: String ->

                        textFieldText.value = text
                    },
                    readOnly = isFieldReadOnly,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    label = {

                        Text(text = fieldLabel)
                    },
                    maxLines = fieldMaxLines,
                    keyboardOptions = keyboardOptions
                )
            },
            confirmButton = {

                Button(
                    onClick = {

                        context.setPreference(key = preferenceData.key, value = textFieldText.value)

                        isDialog.value = isDialog.value.not()
                    }
                ) {

                    Text(text = ConstantButton.SAVE_BUTTON)
                }
            },
            dismissButton = {

                OutlinedButton(
                    onClick = {

                        isDialog.value = isDialog.value.not()
                    }
                ) {

                    Text(text = ConstantButton.DISMISS_BUTTON)
                }
            }
        )
    }

    PreferenceCard(
        preferenceTitle = preferenceData.title,
        preferenceSummary = getFieldText,
        isEnabled = isEnabled,
        onClick = {

            isDialog.value = isDialog.value.not()
        }
    )
}