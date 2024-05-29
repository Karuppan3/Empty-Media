package io.bash_psk.preference.preference

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.bash_psk.preference.datastore.PreferenceData
import io.bash_psk.preference.datastore.getPreference
import io.bash_psk.preference.datastore.setPreference
import io.bash_psk.preference.resource.ConstantDesc

@Composable
fun DropDownPreference(
    preferenceData: PreferenceData<String, String, String>,
    isEnabled: Boolean = true
) {

    val context = LocalContext.current

    val getSelectedItem by context.getPreference(
        key = preferenceData.key,
        initial = preferenceData.initial
    ).collectAsStateWithLifecycle(initialValue = preferenceData.initial)

    val isMenuExpanded = rememberSaveable {
        mutableStateOf(value = false)
    }

    PreferenceCard(
        preferenceTitle = preferenceData.title,
        preferenceSummary = getSelectedItem,
        isEnabled = isEnabled,
        onClick = {

            isMenuExpanded.value = isMenuExpanded.value.not()
        },
        trailingContent = {

            DropdownMenu(
                modifier = Modifier.fillMaxWidth(fraction = 0.75f),
                expanded = isMenuExpanded.value,
                onDismissRequest = {

                    isMenuExpanded.value = isMenuExpanded.value.not()
                }
            ) {

                preferenceData.entities.toList().forEach { itemMenu: Pair<String, String> ->

                    DropdownMenuItem(
                        text = {

                            Text(
                                text = itemMenu.second,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        trailingIcon = {

                            when (getSelectedItem) {

                                itemMenu.second -> {

                                    Icon(
                                        imageVector = Icons.Filled.Check,
                                        contentDescription = ConstantDesc.LEAD_ICON_DESC
                                    )
                                }
                            }
                        },
                        onClick = {

                            context.setPreference(key = preferenceData.key, value = itemMenu.second)

                            isMenuExpanded.value = isMenuExpanded.value.not()
                        }
                    )
                }
            }
        }
    )
}