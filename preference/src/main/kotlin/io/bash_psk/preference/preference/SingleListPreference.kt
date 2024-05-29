package io.bash_psk.preference.preference

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.bash_psk.preference.datastore.PreferenceData
import io.bash_psk.preference.datastore.getPreference
import io.bash_psk.preference.datastore.setPreference
import io.bash_psk.preference.resource.ConstantButton

@Composable
fun <T> SingleListPreference(
    preferenceData: PreferenceData<T, String, T>,
    dialogTitle: String,
    isEnabled: Boolean = true,
    isDismissOnBackPress: Boolean = true,
    isDismissOnClickOutside: Boolean = true
) {

    val context = LocalContext.current

    val getSelectedItem by context.getPreference(
        key = preferenceData.key,
        initial = preferenceData.initial
    ).collectAsStateWithLifecycle(initialValue = preferenceData.initial)

    val isDialog = rememberSaveable {
        mutableStateOf(value = false)
    }

    val itemList = rememberSaveable {
        mutableStateOf(value = preferenceData.entities.toList())
    }

    val itemMapReversed = PreferenceUtils.getReversedMap(
        itemMap = preferenceData.entities
    )
    
    val selectedItem = rememberSaveable(getSelectedItem) {
        mutableStateOf(value = getSelectedItem)
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

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(space = 4.dp),
                    horizontalAlignment = Alignment.Start
                ) {

                    items(
                        items = itemList.value
                    ) { item: Pair<String, T> ->

                        val isSelected = selectedItem.value == item.second

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = isSelected,
                                    onClick = {

                                        selectedItem.value = item.second
                                    }
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
                        ) {

                            RadioButton(
                                selected = isSelected,
                                onClick = {

                                    selectedItem.value = item.second
                                }
                            )

                            Text(
                                text = item.first,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            },
            confirmButton = {

                Button(
                    onClick = {

                        context.setPreference(key = preferenceData.key, value = selectedItem.value)

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
        preferenceSummary = "${itemMapReversed[getSelectedItem]}",
        isEnabled = isEnabled,
        onClick = {

            isDialog.value = isDialog.value.not()
        }
    )
}