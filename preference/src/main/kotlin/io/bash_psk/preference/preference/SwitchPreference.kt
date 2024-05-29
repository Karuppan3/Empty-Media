package io.bash_psk.preference.preference

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.bash_psk.preference.datastore.PreferenceData
import io.bash_psk.preference.datastore.getPreference
import io.bash_psk.preference.datastore.setPreference
import io.bash_psk.preference.resource.ConstantDesc
import io.bash_psk.preference.resource.ConstantString

@Composable
fun SwitchPreference(
    preferenceData: PreferenceData<Boolean, String, String>,
    isEnabled: Boolean = true
) {

    val context = LocalContext.current

    val getSwitchState by context.getPreference(
        key = preferenceData.key,
        initial = preferenceData.initial
    ).collectAsStateWithLifecycle(initialValue = preferenceData.initial)

    PreferenceCard(
        preferenceTitle = preferenceData.title,
        preferenceSummary = when (getSwitchState) {

            true -> {

                ConstantString.ENABLED
            }

            false -> {

                ConstantString.DISABLED
            }
        },
        isEnabled = isEnabled,
        onClick = {

            context.setPreference(key = preferenceData.key, value = getSwitchState.not())
        },
        trailingContent = {

            Switch(
                enabled = isEnabled,
                checked = getSwitchState,
                thumbContent = {

                    Icon(
                        modifier = Modifier.size(
                            size = SwitchDefaults.IconSize
                        ),
                        imageVector = when (getSwitchState) {

                            true -> {

                                Icons.Filled.Check
                            }

                            false -> {

                                Icons.Filled.Close
                            }
                        },
                        contentDescription = ConstantDesc.SWITCH_THUMB_DESC
                    )
                },
                onCheckedChange = { isChecked: Boolean ->

                    context.setPreference(key = preferenceData.key, value = isChecked)
                }
            )
        }
    )
}